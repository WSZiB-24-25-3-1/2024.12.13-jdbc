package pl.edu.wszib.jdbc;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    private String login;
    private String password;
    private String name;
}
