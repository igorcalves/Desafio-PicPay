package com.picpayPortifolio.picpayPortifolio.services;

import com.picpayPortifolio.picpayPortifolio.domain.user.User;
import com.picpayPortifolio.picpayPortifolio.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email  = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);


        ResponseEntity<String> notificationResponse = null;
        try {
            notificationResponse = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6",notificationRequest,String.class);
        } catch (ResourceAccessException e) {
            throw new RuntimeException(e);
        }


        if(notificationResponse.getStatusCode() != HttpStatus.OK){
            System.out.print("Erro ao enviar notificação");
            throw new Exception("Serviço de notificação esta fora do ar");
        }

    }
}
