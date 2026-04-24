package com.retrobbs.backend.infrastructure.adapters.out.redis;

import com.retrobbs.backend.domain.model.RankingResult;
import com.retrobbs.backend.domain.ports.out.RankingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RankingRedisAdapter implements RankingStore {

    private static final String RANKING_KEY = "retrobbs:ranking:global";
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void agregarPuntos(String username, double puntos) {
        redisTemplate.opsForZSet().incrementScore(RANKING_KEY, username, puntos);
    }

    @Override
    public void quitarPuntos(String username, double puntos) {
        redisTemplate.opsForZSet().incrementScore(RANKING_KEY, username, -puntos);
    }

    @Override
    public List<RankingResult> obtenerTop10() {
        Set<ZSetOperations.TypedTuple<String>> resultado =
                redisTemplate.opsForZSet().reverseRangeWithScores(RANKING_KEY, 0, 9);

        List<RankingResult> ranking = new ArrayList<>();
        int posicion = 1;

        if (resultado != null) {
            for (ZSetOperations.TypedTuple<String> entry : resultado) {
                double puntos = entry.getScore() != null ? entry.getScore() : 0;
                ranking.add(RankingResult.builder()
                                    .posicion(posicion++)
                                    .username(entry.getValue())
                                    .puntos(puntos)
                                    .nivel(calcularNivel(puntos))
                                    .build());
            }
        }
        return ranking;
    }

    @Override
    public Double obtenerPuntos(String username) {
        return redisTemplate.opsForZSet().score(RANKING_KEY, username);
    }

    private String calcularNivel(double puntos) {
        if (puntos >= 500) return "LEYENDA";
        if (puntos >= 200) return "EXPERTO";
        if (puntos >= 50)  return "REGULAR";
        return "NOVATO";
    }
}