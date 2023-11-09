package com.fernandez.quart.service;

import com.fernandez.quart.dto.UrlInfoDTO;
import com.fernandez.quart.entity.Urls;
import com.fernandez.quart.repository.UrlsRepository;
import com.fernandez.quart.utils.UtilsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UrlProcessingServiceImpl implements UrlProcessingService {

    @Autowired
    private UrlsRepository urlsRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public void processUrls() {
        List<Urls> resultados = urlsRepository.findAll();

        UtilsClass utilsClass = new UtilsClass();
        List<String> strings = utilsClass.listUrls(resultados);

        Set<String> uniqueFirstParts = strings.stream()
                .map(s -> s.split(","))
                .filter(parts -> parts.length >= 1)
                .map(parts -> parts[0])
                .collect(Collectors.toSet());

        Map<String, Long> countByFirstPart = strings.stream()
                .map(s -> s.split(","))
                .filter(parts -> parts.length >= 1)
                .map(parts -> parts[0])
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        UrlInfoDTO urlInfoDTO = new UrlInfoDTO(countByFirstPart, strings, uniqueFirstParts.size());
        kafkaProducerService.sendMessageToKafkaTopic("COMPETICIONES", urlInfoDTO.toString());
    }
}
