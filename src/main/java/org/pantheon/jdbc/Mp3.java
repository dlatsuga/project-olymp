package org.pantheon.jdbc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Mp3 {
    private int id;
    private Author author;
    private String song;
}
