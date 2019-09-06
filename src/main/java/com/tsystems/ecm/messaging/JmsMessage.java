package com.tsystems.ecm.messaging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class JmsMessage implements Serializable {

    private String message;

}
