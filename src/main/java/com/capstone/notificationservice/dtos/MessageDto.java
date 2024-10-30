package com.capstone.notificationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto {
    private String to;
    private String from;
    private String subject;
    private String body;
}
