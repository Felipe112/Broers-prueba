package com.broers.controller.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {

  private Long id;

  private String name;

  private String email;

  private LocalDate createdAt;

  private boolean isActive;

  private String password;

}
