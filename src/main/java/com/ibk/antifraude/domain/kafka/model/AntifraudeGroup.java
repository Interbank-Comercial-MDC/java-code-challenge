package com.ibk.antifraude.domain.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AntifraudeGroup {
    private Long id;
    private String name;
    private int value;
    private LocalDateTime createdAt;
}
