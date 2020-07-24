package com.zyz.sparrow.config;




import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PreciseCityIdAlgeorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<Integer> shardingValue) {
        for (String each : availableTargetNames) {
            int cloumValue = shardingValue.getValue();
            //最后两位表示组织机构的市级编码，省级为00
             int tableEnd = cloumValue%2;
            if (each.endsWith(tableEnd+"")){
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
