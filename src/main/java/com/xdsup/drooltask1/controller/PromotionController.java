package com.xdsup.drooltask1.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xdsup.drooltask1.entity.RequestBean;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PromotionController {
    private final KieContainer kieContainer;

    @PostMapping(path = "/api/promo/calc_balls")
    public Object calcBallsFromAmount(@RequestBody RequestBean requestBean) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(requestBean);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.destroy();
        log.info(ruleFiredCount + " rules were applied");
        return new Object() {
            @Getter @JsonProperty("discounted_balls")
            final int discountedBalls = requestBean.getBalls();
        };
    }
}
