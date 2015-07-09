package com.guigarage.animations.transitions;

@FunctionalInterface
public interface FunctionalTransition {
    void interpolate(double frac);
}
