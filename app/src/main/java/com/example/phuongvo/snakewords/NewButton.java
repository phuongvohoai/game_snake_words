package com.example.phuongvo.snakewords;

import android.widget.Button;

public class NewButton {

	vector2d Location;
	Button btn;
	boolean clicked;
	boolean allow;
	NewButton(){clicked=false;allow=false;};
	NewButton(vector2d v, Button b, boolean c, boolean a)
	{
		Location= v;
		btn=b;
		clicked=c;
		allow=a;
	}
}
