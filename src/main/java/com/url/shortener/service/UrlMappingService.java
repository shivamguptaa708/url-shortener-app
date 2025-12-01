package com.url.shortener.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.url.shortener.dtos.ClickEventDto;
import com.url.shortener.dtos.UrlMappingDto;
import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repository.ClickEventRepository;
import com.url.shortener.repository.UrlMappingRepository;

@Service
public class UrlMappingService {
	
	private final UrlMappingRepository urlMappingRepository;
	private final ClickEventRepository clickEventRepository;
	
	public UrlMappingService(UrlMappingRepository urlMappingRepository, ClickEventRepository clickEventRepository ) {
		this.urlMappingRepository = urlMappingRepository;
		this.clickEventRepository= clickEventRepository;
		
	}

	public UrlMappingDto createShortUrl(String originalUrl, User user) {
        
		String shortUrl = generateShortUrl();
		UrlMapping urlMapping = new UrlMapping();
		urlMapping.setOriginalUrl(originalUrl);
		urlMapping.setUser(user);
		urlMapping.setShortUrl(shortUrl);
	    urlMapping.setCreatedDate(LocalDateTime.now());
	    UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
	    return convertToDto(savedUrlMapping);
	    
	}

	private UrlMappingDto convertToDto(UrlMapping urlMapping) {
		UrlMappingDto urlMappingDto = new UrlMappingDto();
		
	        urlMappingDto.setId(urlMapping.getId());
	        urlMappingDto.setOriginalUrl(urlMapping.getOriginalUrl());
	        urlMappingDto.setShortUrl(urlMapping.getShortUrl());
	        urlMappingDto.setClickCount(urlMapping.getClickCount());
	        urlMappingDto.setCreatedDate(urlMapping.getCreatedDate());
	        urlMappingDto.setUsername(urlMapping.getUser().getUsername());
	        return urlMappingDto;
		

	}

	private String generateShortUrl() {
		
		 String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		
		Random random = new Random();
		
		StringBuilder shortUrl = new StringBuilder();
		
		for(int i=0; i<8; i++)
		{
			shortUrl.append(characters.charAt(random.nextInt(characters.length())));
		}
		return shortUrl.toString();	
	}

	public List<UrlMappingDto> getUrlsByUser(User user) {
		
		return urlMappingRepository.findByUser(user).stream()
				.map(this::convertToDto)
				.toList();
	}

    public List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickEventDto clickEventDto = new ClickEventDto();
                        clickEventDto.setClickDate(entry.getKey());
                        clickEventDto.setCount(entry.getValue());
                        return clickEventDto;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

	public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
	
		List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
		List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
		return clickEvents.stream().collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));
	}

	public UrlMapping getOriginalUrl(String shortUrl) {
		
		UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
		if(urlMapping != null)
		{
			urlMapping.setClickCount(urlMapping.getClickCount() + 1);
			urlMappingRepository.save(urlMapping);
			
			//record click event
			ClickEvent clickEvent = new ClickEvent();
			clickEvent.setClickDate(LocalDateTime.now());
			clickEvent.setUrlMapping(urlMapping);
			clickEventRepository.save(clickEvent);
			
		}
		return urlMapping;
	}
	

	
}
