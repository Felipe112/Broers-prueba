package com.broers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_users", schema = "broersdb")
public class User extends AbstractEntity {

  @Column(name = "full_name")
  private String name;

  @Email(regexp = "^[a-zA-Z0-9]{1,50}@[a-zA-Z0-9]{1,50}\\.[a-zA-Z]{2,}$",
      message = "El formato de correo electonico no es el adecuado")
  @Column(unique = true)
  private String email;

  private String password;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDate createdAt;

  private boolean active = false;

}
