package vlg.jli.tracker.Intro;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.R;
import vlg.jli.tracker.User.UserActivity;
import vlg.jli.tracker.User.UserListActivity;

/**
 * Created by johnli on 3/27/15.
 */
public class SignupPageFragment extends Fragment{

    Button getStartedButton;
    EditText nameTextField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_intro_signup, container, false);
        getStartedButton = (Button) v.findViewById(R.id.get_started_button);
        nameTextField = (EditText) v.findViewById(R.id.username_textfield);

        switchToGetStartedButtonAction();
        return v;
    }

    void switchToSearchButtonAction()
    {
        getStartedButton.setText("Search");
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GameMEAPI api = new GameMEAPI(getActivity().getApplicationContext());
                api.getUserSearch(nameTextField.getText().toString(), new AsyncListener() {
                    @Override
                    public void onResult(Object response, boolean isSuccess) {
                        Intent playerListIntent = new Intent(getActivity().getApplicationContext(), UserListActivity.class);
                        Gson gson = new Gson();
                        playerListIntent.putExtra("searchResult",  gson.toJson(response));
                        playerListIntent.putExtra("isHomeEnabled",  false);
                        startActivity(playerListIntent);
                    }
                });

            }
        });

    }

    void switchToGetStartedButtonAction()
    {
        getStartedButton.setText("Get Started");
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButtonMoveDownAnimation();
                startTextFieldScaleAnimation();
            }
        });

    }

    void startButtonMoveDownAnimation()
    {
        ObjectAnimator animY = ObjectAnimator.ofFloat(getStartedButton, "translationY", 100f);

        animY.start();

        switchToSearchButtonAction();
    }


    void startTextFieldScaleAnimation()
    {
        ScaleAnimation scaleToOne = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, (float)0.5, Animation.RELATIVE_TO_SELF, (float)0.5);
        scaleToOne.setFillAfter(true);
        scaleToOne.setDuration(600);
        nameTextField.setVisibility(View.VISIBLE);
        nameTextField.setAnimation(scaleToOne);
        nameTextField.animate();
    }



}
