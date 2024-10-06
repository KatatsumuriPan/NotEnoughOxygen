package kpan.not_enough_oxygen.block.item;

import kpan.not_enough_oxygen.util.interfaces.block.IHasTEISR;
import net.minecraft.block.Block;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class ItemBlockAnimating extends ItemBlockBase implements IAnimatable, IHasTEISR {

    private final AnimationFactory factory = new AnimationFactory(this);

    public ItemBlockAnimating(Block block) {
        super(block);
    }

    // IAnimatable
    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(
                new AnimationController<>(this, "controller", 0, event -> {
                    event.getController().setAnimation(new AnimationBuilder().loop("animation.coalGenerator.stopped"));
                    return PlayState.CONTINUE;
                }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

}
