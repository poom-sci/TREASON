package element;

import javafx.scene.media.AudioClip;

public class AudioLoader {
	
	public static AudioClip Explosion_Sound=new AudioClip(ClassLoader.getSystemResource("audio/explosion_sound.wav").toString());
	public static AudioClip Granade_Sound=new AudioClip(ClassLoader.getSystemResource("audio/granade_sound.wav").toString());
	public static AudioClip Gun_Sound=new AudioClip(ClassLoader.getSystemResource("audio/gun_sound.wav").toString());
	public static AudioClip Sword_Sound=new AudioClip(ClassLoader.getSystemResource("audio/sword_sound.wav").toString());
	
	public static AudioClip Gameover_Sound=new AudioClip(ClassLoader.getSystemResource("audio/gameover_sound.wav").toString());
	public static AudioClip Mouse_Enter_Sound=new AudioClip(ClassLoader.getSystemResource("audio/mouse_enter_sound.wav").toString());
	public static AudioClip Mouse_Pressed_Sound=new AudioClip(ClassLoader.getSystemResource("audio/mouse_pressed_sound.wav").toString());
	
	public static AudioClip Off_Limits=new AudioClip(ClassLoader.getSystemResource("audio/Off_limits.wav").toString());
	public static AudioClip Star_Commander=new AudioClip(ClassLoader.getSystemResource("audio/Star_Commander.wav").toString());
	
}
