package com.example.therepaintinggameweb.logic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CellWrapper {
    private boolean isCaptured;
    private int value;
}
