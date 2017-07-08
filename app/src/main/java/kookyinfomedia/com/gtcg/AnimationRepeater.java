package kookyinfomedia.com.gtcg;

import android.view.View;
import android.view.animation.Animation;

public class AnimationRepeater implements Animation.AnimationListener
{
    private View view;
    private Animation animation;
    private int count;

    public AnimationRepeater(View view, Animation animation)
    {
        this.view = view;
        this.animation = animation;
        this.count = -1;
    }

    public AnimationRepeater(View view, Animation animation, int count)
    {
        this.view = view;
        this.animation = animation;
        this.count = count;
    }

    public void start()
    {
        this.view.startAnimation(this.animation);
        this.animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) { }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        if (this.count == -1)
            this.view.startAnimation(animation);
        else
        {
            if (count - 1 >= 0)
            {
                this.animation.start();
                count --;
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) { }
}
