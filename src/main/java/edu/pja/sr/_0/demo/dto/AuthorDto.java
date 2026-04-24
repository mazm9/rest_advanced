package edu.pja.sr._0.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
}