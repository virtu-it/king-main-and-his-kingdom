package com.bnp.kingmainandhiskingdom;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class KingMainAndHisKingdomApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(KingMainAndHisKingdomApplication.class).run(args);
        //SpringApplication.run(KingMainAndHisKingdomApplication.class, args);
    }

}
