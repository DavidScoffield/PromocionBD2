package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.TotalAccidentsInLocationRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories.MongoAccidentRepository;
import ar.edu.unlp.info.bd2.promocionbd2.repositories.PostgresAccidentRepository;

@Service
public class AccidentServiceImplementation implements AccidentService {

    @Autowired
    private PostgresAccidentRepository postgresAccidentRepository;

    @Autowired
    private MongoAccidentRepository mongoAccidentRepository;

    public HashMap<String, Integer> getPaginationInfo(Page<Object> page) {
        HashMap<String, Integer> paginationInfo = new HashMap<>();

        paginationInfo.put("totalPages", page.getTotalPages());
        paginationInfo.put("currentPage", page.getPageable().getPageNumber() + 1);
        paginationInfo.put("perPage", page.getPageable().getPageSize());
        paginationInfo.put("totalElements", (int) page.getTotalElements());
        paginationInfo.put("totalElementsInCurrentPage", page.getNumberOfElements());

        return paginationInfo;
    }

    public HashMap<Object, Object> getAccidentsBetweenDates(String startDate, String endDate, int page, int perPage) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = formatter.parse(startDate);
        Date end = formatter.parse(endDate);

        page = page - 1;
        Page<Accident> accidentsPage = postgresAccidentRepository.findAllByStartTimeBetween(start, end, PageRequest.of(page, perPage));
        HashMap<Object, Object> result = new HashMap<>();

        result.put("paginationInfo", getPaginationInfo(accidentsPage.map((Accident a) -> {return null;})));
        result.put("content", accidentsPage.getContent());

        return result;
    }

    @Override
    public List<Accident> getAccidentsNearLocation(Double longitude, Double latitude, Double radius) throws Exception {

        if ((!(longitude >= -180.0 && longitude <= 180.0)) || (!(latitude >= -90 && latitude <= 90))) {
            throw new Exception("Longitude or latitude value out of bounds.");
        }

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);

        List<Accident> accidents = mongoAccidentRepository.findAllByLocationNear(point, distance)
                .getContent()
                .stream()
                .map(GeoResult::getContent)
                .collect(Collectors.toList());

        return accidents;
    }

    @Override
    public HashMap<String, Object> getAverageDistance() {
        HashMap<String, Object> result = new HashMap<>();

        try {
            // Retorna la distancia en metros
            Double distance = postgresAccidentRepository.getAverageDistance() * 1609.34;
            result.put("averageDistance", distance);
        } catch (NullPointerException e) {
            result.put("averageDistance", "No distance found");
        }

        return result;
    }

    /* TODO check parameters and performance */
    @Override
    public List<NearAccidentsSeverityRepresentation> getMostDangerousPoints(Double radius, Integer amount) {
        int maxThreads = amount < 100 ? amount : 100, i;
        PriorityQueue<NearAccidentsSeverityRepresentation> maxHeaps[] = new PriorityQueue[maxThreads];
        Lock lock[] = new ReentrantLock[maxThreads];
        int totalIterations[] = {0};

        for (i = 0; i < maxThreads; i++) {
            maxHeaps[i] = new PriorityQueue<>((a, b) -> {
                return (b != null ? b.getTotalSeverity() : 0) - (a != null ? a.getTotalSeverity() : 0);
            });
            lock[i] = new ReentrantLock();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);
        
        mongoAccidentRepository.findLocationsWithMostAccidents(amount).forEach( (TotalAccidentsInLocationRepresentation location) -> {
            int j = totalIterations[0]++ % maxThreads;
            Thread thread = new Thread(new Worker(maxHeaps[j], location, radius, amount, lock[j]));
            executorService.execute(thread);
        } );

        executorService.shutdown();
        while (!executorService.isTerminated()) {}

        PriorityQueue<NearAccidentsSeverityRepresentation> maxHeap = new PriorityQueue<>((a, b) -> {
            return (b != null ? b.getTotalSeverity() : 0) - (a != null ? a.getTotalSeverity() : 0);
        });
        List<NearAccidentsSeverityRepresentation> result = new ArrayList<>();

        for (i = 0; i < maxThreads; i++) {
            while (!maxHeaps[i].isEmpty()) {
                maxHeap.add(maxHeaps[i].poll());
            }
        }

        for (i = 0; i < amount; i++) {
            result.add(maxHeap.poll());
        }

        return result;
    }

    public class Worker implements Runnable {

        private TotalAccidentsInLocationRepresentation location;
        private double radius;
        private int amount;
        private PriorityQueue<NearAccidentsSeverityRepresentation> maxHeap;
        private Lock lock;

        public Worker(PriorityQueue<NearAccidentsSeverityRepresentation> maxHeap, TotalAccidentsInLocationRepresentation location, double radius, int amount, Lock lock) {
            this.maxHeap = maxHeap;
            this.location = location;
            this.radius = radius;
            this.amount = amount;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                NearAccidentsSeverityRepresentation nearAccidents = mongoAccidentRepository.getNearAccidentsSeverity(location, radius);
                lock.lock();
                updateMax(nearAccidents);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                lock.unlock();
            }
        }

        public void updateMax(NearAccidentsSeverityRepresentation nearAccidents) throws Exception {
            int totalSeverity = nearAccidents.getTotalSeverity();
            if (maxHeap.size() == amount && totalSeverity > maxHeap.peek().getTotalSeverity()) {
                maxHeap.poll();
            }
            if (maxHeap.size() < amount) {
                maxHeap.add(nearAccidents);
            }
        }
    }

    @Override
    public List<String> getFiveStreetsWithMostAccidents() {
        Pageable pageable = PageRequest.of(0, 5);

        return postgresAccidentRepository.getFiveStreetsWithMostAccidents(pageable).getContent();
    }

    @Override
    public HashMap<Object, Object> getAverageDistanceToCloseAccidents(int page, int perPage) {
        page = page - 1;
        Page<Accident> accidentsPage = mongoAccidentRepository.findAllBy(PageRequest.of(page, perPage));
        HashMap<Object, Object> result = new HashMap<>();

        result.put("paginationInfo", getPaginationInfo(accidentsPage.map((Accident a) -> {return null;})));
        result.put("content", mongoAccidentRepository.getAverageDistanceToNearAccidents(accidentsPage.getContent()));
        
        return result;
    }

    @Override
    public HashMap<String, Object> getCommonAccidentConditions() {
        Pageable pageable = PageRequest.of(0, 1);
        int totalResults = 5;
        Object results[] = new Object[totalResults];
        Thread threads[] = new Thread[totalResults];
        HashMap<String, Object> hashMap = new HashMap<>();

        for (int i = 0; i < totalResults; i++) {
            final int j = i; 
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Object result = null;
                    switch(j) {
                        case 0:
                            result = postgresAccidentRepository.getCommonAccidentWeatherCondition(pageable);
                            break;
                        case 1:
                            result = postgresAccidentRepository.getCommonAccidentWindDirection(pageable);
                            break;
                        case 2:
                            result = postgresAccidentRepository.getCommonAccidentVisibility(pageable);
                            break;
                        case 3:
                            result = postgresAccidentRepository.getCommonAccidentHumidity(pageable);
                            break;
                        case 4:
                            result = postgresAccidentRepository.getCommonAccidentStartHour(pageable);
                            break;
                    }
                    results[j] = ((Page<Object>) result).getContent().get(0);
                }
            });

            threads[i] = thread;
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {}

        hashMap.put("commonAccidentWeatherCondition", results[0]);
        hashMap.put("commonAccidentWindDirection", results[1]);
        hashMap.put("commonAccidentVisibility", results[2]);
        hashMap.put("commonAccidentHumidity", results[3]);
        hashMap.put("commonAccidentStartHour", results[4]);

        return hashMap;
    }

}