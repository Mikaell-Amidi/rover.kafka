package com.nordic.strategy;

import java.util.List;

public abstract class StrategyContext<R> {

    public StrategyContext(List<Strategy<R>> strategies) {
        this.strategies = strategies;
    }

    public void trigger(R r) {
        strategies.forEach(o -> o.trigger(r));
    }

    public List<Strategy<R>> strategy(){
        return this.strategies;
    }

    private final List<Strategy<R>> strategies;
}
