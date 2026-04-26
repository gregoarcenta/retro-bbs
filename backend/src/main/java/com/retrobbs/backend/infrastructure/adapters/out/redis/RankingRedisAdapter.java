package com.retrobbs.backend.infrastructure.adapters.out.redis;

import com.retrobbs.backend.domain.model.RankingResult;
import com.retrobbs.backend.domain.ports.out.RankingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RankingRedisAdapter implements RankingStore {

    private final RedisTemplate<String, String> redisTemplate;

    private String getRankingKey() {
        return "retrobbs:ranking:" +
               LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    private String getRankingKey(LocalDate mes) {
        return "retrobbs:ranking:" +
               mes.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    @Override
    public void agregarPuntos(String username, double puntos) {
        String key = getRankingKey();
        redisTemplate.opsForZSet().incrementScore(key, username, puntos);

        // Si la key es nueva, le ponemos 60 días de vida
        // 30 del mes actual + 30 de gracia para consultar el mes siguiente
        Long ttlActual = redisTemplate.getExpire(key, TimeUnit.DAYS);
        if (ttlActual == null || ttlActual == -1) {
            redisTemplate.expire(key, 60, TimeUnit.DAYS);
        }
    }

    @Override
    public void quitarPuntos(String username, double puntos) {
        String key = getRankingKey();
        redisTemplate.opsForZSet().incrementScore(key, username, -puntos);
    }

    @Override
    public List<RankingResult> obtenerTop10() {
        // Por defecto siempre el mes actual
        return obtenerTop10DelMes(LocalDate.now());
    }

    @Override
    public List<RankingResult> obtenerTop10DelMes(LocalDate mes) {
        String key = getRankingKey(mes);

        Set<ZSetOperations.TypedTuple<String>> resultado =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 9);

        List<RankingResult> ranking = new ArrayList<>();
        int posicion = 1;

        if (resultado != null) {
            for (ZSetOperations.TypedTuple<String> entry : resultado) {
                double pts = entry.getScore() != null ? entry.getScore() : 0;
                ranking.add(RankingResult.builder()
                                    .posicion(posicion++)
                                    .username(entry.getValue())
                                    .puntos(pts)
                                    .nivel(calcularNivel(pts))
                                    .build());
            }
        }

        return ranking;
    }

    @Override
    public Double obtenerPuntos(String username) {
        return redisTemplate.opsForZSet().score(getRankingKey(), username);
    }

    private String calcularNivel(double puntos) {
        if (puntos >= 500) return "LEYENDA";
        if (puntos >= 200) return "EXPERTO";
        if (puntos >= 50)  return "REGULAR";
        return "NOVATO";
    }
}