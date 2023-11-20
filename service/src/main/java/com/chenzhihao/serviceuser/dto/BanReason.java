package com.chenzhihao.serviceuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanReason {
    private Integer id;
    private String description;
    private Integer type;
}
