package kpan.not_enough_oxygen.neo_world;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.MathHelper;

public class WeightedList<T> {
    public static <T> WeightedList<T> single(T value) {
        WeightedList<T> res = new WeightedList<>();
        res.add(1, value);
        return res;
    }
    private final List<T> values = new ArrayList<>();
    private final FloatList weights = new FloatArrayList();

    private float totalWeights = 0;

    public void add(float weight, T value) {
        totalWeights += weight;
        weights.add(totalWeights);
        values.add(value);
    }

    public T get(float f) {
        f = MathHelper.clamp(f, 0, 1) * totalWeights;
        for (int i = 0; i < weights.size(); i++) {
            if (f <= weights.get(i))
                return values.get(i);
        }
        return values.get(values.size() - 1);
    }
}
