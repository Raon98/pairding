package com.pairding.global.security;

import io.hypersistence.tsid.TSID;

import java.util.concurrent.ThreadLocalRandom;

public class TsidGenerator {

    private static final TSID.Factory TSID_FACTORY;

    static {
        int nodeId = Integer.getInteger("node.id", ThreadLocalRandom.current().nextInt(0, 256));
        TSID_FACTORY = TSID.Factory.builder()
                .withNodeBits(8)
                .withNode(nodeId)
                .build();
    }

    // TSID 객체 반환
    public static TSID nextTsid() {
        return TSID_FACTORY.generate();
    }

    // DB 저장을 위한 Long 값 반환 (가장 많이 씀)
    public static Long nextId() {
        return TSID_FACTORY.generate().toLong();
    }

    // 로그용/URL용 문자열 반환 (0A1B2C...)
    public static String nextString() {
        return TSID_FACTORY.generate().toString();
    }
}