package com.f1sh.cloudpicbackend.common;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LogUtils {

    public static String nowTime() {
        return DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public static void infoWithTime(String msg) {
        log.info("--------{} {}", nowTime(), msg);
    }

    public static void errorWithTime(String msg) {
        log.error("!!!!!!!!{} {}", nowTime(), msg);
    }

    public static void warnWithTime(String msg) {
        log.warn("========{} {}", nowTime(), msg);
    }
}
