package com.bnp.kingmainandhiskingdom.services;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Qualifier("ARMY")
@Retention(RetentionPolicy.RUNTIME)
public @interface Army {
}
