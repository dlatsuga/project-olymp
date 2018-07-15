package org.pantheon.jdbc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Author {
    private int id;
    private String name;
}
