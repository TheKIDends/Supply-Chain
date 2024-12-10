package com.supplychain.commonservice.query;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailsUserQuery {
    private String id;
    private String userName;
}