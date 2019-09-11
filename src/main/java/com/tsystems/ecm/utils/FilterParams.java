package com.tsystems.ecm.utils;

import lombok.*;

/**
 * Utility class containing user selected filter and current page.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FilterParams {

    /**
     * The filter parameter.
     */
    private String filter;

    /**
     * The current page parameter.
     */
    private String page;

}
