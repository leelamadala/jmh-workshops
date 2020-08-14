/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomekl007.chapter_4;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author MyMac
 */
public class PotentialMemoryLeak {
    
    private CacheLoader<String, String> loader = new CacheLoader<String, String>(){
        @Override
        public String load(String key){
            return key.toUpperCase();
        }
    };
    
    private LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(120, TimeUnit.MINUTES)
            .build(loader);
    
    void addToCache(String k, String v){
        cache.put(k, v);
    }
    
    void getAndLoadIfAbsent(String k){
        cache.getUnchecked(k);
    }
    //1. start with VM Options:
    //-XX:+UnlockCommercialFeatures
    //-XX:+FlightRecorder
    //-XX:StartFlilghtRecording=duration=60s, filename=myrecording.jfr
    //2. open recording file jmc -open myrecording.jfr
    //Home ./bin/jfr sufmmary /User/MyMac/NetBeandr/./././myrecording.jfr

    //pre-11
    //-XX:+unclickCommercialFeatures -XX:+FlightRecorder
    
    public static void main(String[] args){
        PotentialMemoryLeak potentialMemoryLeak = new PotentialMemoryLeak();
        for (int i = 0; i < 100_000_000; i++) {
            potentialMemoryLeak.getAndLoadIfAbsent(String.valueOf(i));
        }
    }
    
}

