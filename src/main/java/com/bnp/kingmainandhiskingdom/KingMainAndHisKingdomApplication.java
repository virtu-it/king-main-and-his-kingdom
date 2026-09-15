package com.bnp.kingmainandhiskingdom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class KingMainAndHisKingdomApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(KingMainAndHisKingdomApplication.class).run(args);
        //SpringApplication.run(KingMainAndHisKingdomApplication.class, args);
    }

}
