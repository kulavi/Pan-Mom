//Checks whether user is remembered or not based on that calls the respective intent
package com.Pan_Mom_Home;




import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;



public class Pan_MomActivity extends CustomWindow  {
	
	Button btnregister,btnlogin,btnreset;
	EditText edtuname,edtpwd;  
	CheckBox chkischeck;
	boolean internet,status;
	InputStream is = null;
	InputStream is1 = null;
	InputStream is3 = null;
	String result =null;
	String [] values;
	String [] week;
	String [] babyinfo;
	String return_flag="Pan_Mom";
	String [] 	arr_due_date;
	String[] week_info_lady,week_info_baby;
	String[] symptom,symptom_descri;
	String uname;
	String sss;
	DatabaseHelper data;
	Pan_mom_session mysession;
	String ret_uname,ret_pass,ret_id;
	String ret_nicename,ret_email,ret_date;
	String ret_name,ret_lmp,ret_duedate,ret_age,ret_occ_self,ret_occ_hus,ret_mobs,ret_mobh,ret_mobg;
	String struname,strpwd,strdue_date;
	int userid;
	private String username;
	private String pwd;
	private String struserid;
	int total=0;
	int a;
	boolean isRunning=false; 
	String signature;
	   
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        /*initializing classes*/
        data=new DatabaseHelper(this);
        mysession=new Pan_mom_session(this);
              
        
        //Add week data............................................
        week=new String[41];
        babyinfo=new String[41];
        values=new String[9];
        
        week[0]="It is important for you and your baby to eat well during pregnancy.Staying fit during pregnancy may offer you a more comfortable 9 months and an easier delivery.Make sure to get a good night’s rest during this time. The better health you are in, the healthier your baby will be.A prenatal vitamin, with 400 micrograms of folic acid, increases the chance of healthy baby and prevents neural tube defects.These products can lead to birth defects such as respiratory problems, low birth weight, and fetal alcohol syndrome.";
        week[1]="It is important for you and your baby to eat well during pregnancy.Staying fit during pregnancy may offer you a more comfortable 9 months and an easier delivery.Make sure to get a good night’s rest during this time. The better health you are in, the healthier your baby will be.A prenatal vitamin, with 400 micrograms of folic acid, increases the chance of healthy baby and prevents neural tube defects.These products can lead to birth defects such as respiratory problems, low birth weight, and fetal alcohol syndrome.";
        week[2]="You are developing a uterine lining.  During this development, the glands and blood vessels in the endometrium are increasing in size and number.In a few weeks, the vascular spaces will completely fuse and interconnect to form the placenta, which will supply oxygen and nutrition to the embryo and fetus.Your body is also providing stimulation for the egg to grow by releasing follicle-stimulating hormones, and this growth will lead to ovulation at the end of the week.Ovulating at this time means that your ovary will release an egg into the fallopian tube.            ";
        week[3]="Your body will release an immunosuppressant protein called Early Pregnancy Factor (EPF) to ensure that your body will not recognize baby as a foreign invader.You may experience a little spotting called implantation bleeding, due to the morula implanting itself in the endometrium. It is highly recommended that you take an adequate amount of folic acid supplements to decrease the chance of neural tube defects.              ";
        week[4]="Implanting is still taking place, but ends around mid-week.  At this point, the blastocyst has developed into an embryo and starts to produce a hormone called hCG (human chorionic gonadotropin), which will stop your monthly ovulations.There is an exchange, however, because though hCG may result in these symptoms, it is also the hormone measured in pregnancy tests, so be sure to take the pregnancy test!  Also, you still have some time before you begin showing.In addition to the spotting that you may continue to experience due to implantation, you may also experience the typical signs of pregnancy, including fatigue, frequent urination, breast pain, and morning sickness.  These symptoms may be deceiving as they may resemble PMS, but you may, in fact, be pregnant.      ";      
        week[5]="Sore breasts, fatigue, backaches, nausea and morning sickness, frequent urination, and mood swings are typical during the 5th week.  If breasts are very tender during sleep, sleeping in a jogging bra or another bra that provides support may help. Heartburn may occur starting this week.  If this is the case, consider eating more frequent, but smaller, meals throughout the day.Headaches may be prone to occur due to a rise in hormone levels.Be even more careful of the foods you eat!  Listeriosis and toxoplasmosis are just two examples of food-borne illnesses that may make your baby susceptible to birth defects or miscarriages.In order to prevent this, be sure to stay clear of certain types of cheeses, unpasteurized foods, and raw and undercooked shellfish, meats, and eggs.  Soiled litter boxes may also contain traces of foodborne illnesses, so do not clean out your own litter box. ";
        week[6]="There will be a great change in your breasts this week.If you haven't experienced all of the symptoms listed in previous weeks, you probably will now.Nausea, vomiting, and aching breasts are going to become a common occurrence.Morning sickness will occur in the morning...noon...and night--don't be fooled by the name.Frequent urination will occur due to an increase in hormonal changes.  Remember, you are now responsible for removing your baby's waste in addition to your own.";
        week[7]="Although your symptoms are relatively the same from last week, your body is continuously changing. If changes in the breasts were the most significant change last week, the biggest change this week will be in the cervix, as a mucous plug will form.This plug will seal the uterus for protection.Be aware that you may notice breakouts on your face due to the change in hormone levels.A steady intake of calcium is vital to prevent such complications like hypertension and pre-eclampsia. Also, to ensure that the calcium that you are taking in is not being absorbed by other nutrients in your body, it is wise to stay away from foods and drinks such as tea, coffee, protein, unleavened bread, and salt.  Keep in mind that you should not take in more than 500mg of calcium at one time. 1200mg of calcium for the day is a safe limit.Now may be a good time to get a good start on taking courses relevant to pregnancy to know what to expect.  Don't worry, however, this guide will continue until you reach your full term of pregnancy!";
        week[8]="Some pregnancy symptoms that you may have encountered are missed periods, nausea, extreme fatigue, or tight clothes. Once you have confirmation of your pregnancy through a home pregnancy test, you should call your healthcare provider and schedule your first prenatal visit.The reason why some practitioners won’t see you until now (missed 2 periods) is that it decreases the amount of false pregnancies and miscarriages. At this prenatal visit, your doctor will confirm that you are pregnant and take tests to check you and your baby’s health. This includes a family history, urine tests, blood tests, weight and blood pressure measurements, and a pelvic exam. Your healthcare provider may also want to do a pap smear or will decide to do one later on.         ";
        week[9]="Stuffy noses and bloody noses are pretty common during pregnancy.  Try using a vaporizer or humidifier to lessen the symptoms.You may also find yourself more tired than before. Your breasts may also feel fuller and heavier from your changing hormones.While your headaches may lessen, remember to walk close to a railing and change position slowly to reduce chances of dizziness. ";
        week[10]="You might feel better this week as your morning sickness begins to ease, but your body should still feel tired and nauseated. Hang in there; it should only be a few more weeks for most of you.Your abdomen may start to pooch out. This will be more from bowel distension that the growth in your uterus.Your weight gain should be steady at this point. If you are not gaining weight, ask your healthcare provider. Though it is different from person to person, weight gain is usually the first indication of a growing baby.As your blood volume increases to accommodate for the baby (by 40-50%), you will notice veins on your breasts, legs, and abdomen more easily. Don’t worry because these veins will disappear when your blood volume returns to normal (after delivery).Remember to continue eating healthily (which includes taking your vitamins), exercising, and resting.";
        week[11]="By this week, your uterus has already grown to the size of a grapefruit! In most cases, you probably haven’t gained much weight, but it is good to know if you’re on track in terms of weight.To fully nourish your baby, weight gain is necessary. Over the entire pregnancy, doctors recommend that you gain 25 to 35 pounds (11.33 to 15.87 kg). During the first trimester, you should be gaining 1-2 pounds a month (totaling to 6 pounds for this trimester).It’s fine if you’re losing weight the first trimester as morning sickness, nausea, or heartburn tend to make it hard to gain weight.As this week progresses, you may find that you’re appetite is returning. It is good to remember that good nutrition is important for both you and your baby. Remember to take your vitamins, eat healthily, and rest.";
        week[12]="You may start feeling better this week from your fatigue and nausea (morning sickness). But don't worry if you don't. Others will hold onto it for a bit longer.You may also notice that you have to take fewer trips to the bathroom as your uterus shifts up and forward, away from the bladder. But don't get too comfortable! By the third trimester, the baby and uterus will be big enough that they will be pressing on the bladder again.In terms of hormones, your placenta has taken over producing hormones which leads to less intense mood swings. If you had corpus luteum cysts earlier, they may begin dissolving now.But, you may be getting lightheaded or headaches this week again. This is from your increase in blood volume. Remember to take caution and not get up too quickly.As you are pregnant, you may notice some changes in your skin. Many women develop a dark line from the belly button downward which is known as a linea nigra.You may notice that you’ve been “glowing.” Your increased blood volume and hormones work together to increase oil gland secretion which leads to a “pregnant glow.”You may also start developing dark patches on the face and neck, known as chloasma";
        week[13]="Besides feeling your best, this is time when you will start to feel pregnant. At this time, the feelings of sickness are (or are about to) end and your tummy is small enough to not get in your way. Remember to take care of your body by taking vitamins and getting rest.You may also be noticing stretch marks from your expanding tummy and breasts. You may also notice some stretch marks around your waist, buttocks and breasts. To help avoid these stretch marks, it is important to keep your weight gain constant, staying active, and maintaining a healthy diet (food, vitamins, and water).You may have delayed spreading the good news, but now would be a great time to tell them. This is also a time when other family members become more active in the pregnancy as it becomes more real. Encourage them to participate in your pregnancy, but only to the extent that you are comfortable with.";
        week[14]="During this week of pregnancy, the hormonal changes in your body are becoming apparent. Though they are steadying and the mood swings from the first trimester will slow, you will see some other significant changes. A dark line may form from the middle of your abdomen to your pubic bone. This is normal and is called the linea negra.The areola (dark part of your nipple area) may have gotten darker and longer. This is normal as your body readies itself for your baby and breast feeding.Another significant change is a possibility for constipation from your intestinal muscles slowing down. This is from lower hormonal levels and your baby pressing against your bowel. A way to combat this is to increase your intake of fiber. If it continues to bother you, make sure to mention it to your doctor.";
        week[15]="During these few weeks, you should schedule your amniocentesis, if you plan on having one. This test is usually done between 16 and 18 weeks of the baby’s birth.Your heart is also working over time to supply your baby with oxygen. It is pumping about 20% more blood than before pregnancy. Your heart will continue increasing the amount of blood that it pumps until it reaches around 30 – 50% over your original amount.As your abdomen increases in size, you may find that it grows harder to find a comfortable sleeping position. For many women, the most comfortable sleeping position is on your left side with a pillow below your legs. Try to sleep in this position as early as possible so that it will be easier before your baby grows any bigger.";
        week[16]="From this week to the 18th week of pregnancy, your doctor may offer you a maternal blood screening test. This test is also known as the “triple marker” test which measures levels of Alpha fetoprotein (AFP), a protein produced by the fetus, and pregnancy hormones hCG and estriol in the mother’s blood. The test tells the mother if their baby is at risk for neural tube defects, such as spina bifida, or chromosomal abnormalities, such as Down syndrome.An increased blood level (mentioned last week) can also cause a stuffy nose or even nose bleeds.You may also start to begin to feel your baby’s movements this week (This is due to hardening bones). These feelings have been said to feel like butterflies in your stomach (or popcorn popping) rather than movement and is called fluttering or quickening. Don’t worry if you still don’t feel anything this week, it’ll come soon enough as your baby moves more frequently and vigorously.";
        week[17]="You may have noticed that your breasts have increased in size since pregnancy began. Many women increase by one or two cup sizes in this time and veins become visible. Hormones are routing blood to your breasts as glands that produce milk are growing for breast feeding. Buy bras in a variety of sizes to support your breast growth.Your uterus has grown to be about 2 inches below your bellybutton. There is now an obvious swelling in your lower abdomen. At this point, maternity clothing is required for comfort and you have probably gained about 5 – 10 pounds.Your body secretions may also increase due to increased blood volume. This could be increased nasal congestion or a runny nose, increased sweating, or increased vaginal discharge. These symptoms are normal and will go away after the birth of your baby.You might also be still experiencing fatigue. If you still feel sleepy, it should start to improve over the next few weeks. Moderate exercise will help combat this fatigue as well.";
        week[18]="Sleeping may be more difficult as your body grows. Try propping yourself up with pillows to help you find a more comfortable sleeping position. You can also try doing pelvic tilts before bed and always urinate before lying down.Don’t worry if your vaginal discharge (usually a whitish color) is increasing as pregnancy continues. The increase is nothing to worry about and is completely normal.You may also be getting light-headed, dizzy, or even faint. This is also normal and the only cause for concern is if it happens more than several times a day or severely (passing out). This is usually caused from moving from one position to another too quickly. The best thing to do is to move slowly when changing positions.As you’re preparing for life with the baby, an important choice is to select a good pediatrician. This is a good week to start scheduling visits to meet the doctors to discuss their practice and procedures. You should be aware of the doctor’s appointment availability, immunization scheduling, emergency situations, and what insurance they accept. It’s important to feel comfortable with your doctor so do your homework and make an educated decision.";
        week[19]="Women wonder if having sex during pregnancy will hurt their baby and the answer is no. Sex is usually safe during your entire pregnancy as long as your pregnancy is normal. On the other hand, you’ll find that your desire for sex fluctuates with your hormones through various stages of pregnancy. Remember to communicate with your partner as these issues come up.Remember to communicate with your partner as these issues come up.Pregnancy mask and dry or flaky skin are common during pregnancy and are due to your hormones fluctuating. Don’t worry because your skin will return to normal after pregnancy. Try using lotions and oils as a temporary treatment and remember to keep a healthy diet and to stay hydrated.About halfway through pregnancy is a great time to look into childbirth classes. There are many types of classes, but they can help with your anxiety and help you understand what is going on in your body.  Make sure to find a class that fits your schedule as some meet for a few hours every week, while others meet for a long time over 1or 2days";

        week[20]="At this point, your healthcare provider may recommend that you have an ultrasound. Most moms have one around this point. The ultrasound can determine the size and position of the fetus and any physical abnormalities. The sex can also be determined at this time.Your uterus should be around your belly button this week. Your healthcare provider should also measure the size of your uterus to keep track of your baby’s development and growth.Your belly button may pop out and stay that way. Some moms report having a little trouble breathing as their lungs are cramped with internal organs. While this is not a serious complication, it can be uncomfortable. But, once your baby “drops” (4-6 weeks before your due date) this pressure will be relieved.As your baby pushes down on your bladder, you may have to urinate more often. Remember to do your pelvic tilts before laying down to give you a few more minutes before using the restroom.";
        week[21]="Your uterus should be about one inch above your belly button this week. Don’t be afraid if your belly button turns inside out. You are also probably showing now, so be sure to invest in some comfortable maternity clothes.Emotionally you should be more stable as your hormones stay level.You should have gained about 10 – 15 pound but don’t worry if it’s a bit off. As long as you maintain a healthy diet and stay hydrated, you should be right on track.Your ankles and feet may have begun swelling at the end of the day. Make sure to drink water and get rest. Plan at least 30 minutes of rest where you will raise your feet to reduce swelling.";
        week[22]="Your uterus should still be about an inch above your belly button.As your baby grows, your uterus will become more and more prominent. As your baby continues to gain weight, you will be heavier. This extra weight may take a toll on your back. Remember to rest your feet by raising them above your hips.You may start noticing your uterus practicing for delivery with painless contractions called Braxton Hicks contractions. Don’t worry if you’re experiencing these as they aren’t dangerous or harmful. If these contractions become more frequent, painful, or intense, contact your health care provider because these may be a sign of preterm labor.";
        week[23]="Your uterus should still be just over an inch (1.18 inches) above your belly button. In terms of weight, you should have gained from 12 to 15 pounds. Again, don’t worry if your weight gain is a little off as every pregnancy is different.During your doctor appointments, your doctor may palpate your abdomen. This will allow your health care provider to feel the position of your baby. A doctor will also measure your fundal height (pubic bone to top of uterus) which is a good measure of the continued growth of your baby.You may also notice that you’re starting to have more and more trouble sleeping. This may be caused from anxiety, urination, heartburn, leg cramps, and discomfort. But rest is important for both you and your baby. Try to play some soothing music, read a relaxing book, or drink a cup of non-caffeinated tea to help you sleep. Also remember to sleep on your side with a pillow between your legs to help relieve pressure.";
        week[24]="Your fundus (top of your uterus) continues to grow and should reach about 1.5 to 2 inches above your navel.An important prenatal test that happens between weeks 24 and 28, is a glucose tolerance test that checks for gestational diabetes. This type of temporary diabetes can cause problems for the baby and should be properly screened for and controlled.You should also be able to feel the movements of your little one and even its sleep cycles. It isn’t a bad idea to sync your rest cycles with your baby’s as well. Remember to stay on top of your nutrition on continue taking your prenatal vitamins and supplements.";
        week[25]="Your uterus now measures around 9.84 inches (25 cm) from the pubic symphysis to the top of your uterus.You probably are still feeling very good. Hopefully, your overall health and energy have improved. Soon you will begin to see your healthcare provider more often to monitor fetal heartbeat, movement, and your fundal height as well as your overall health.Pregnancy can also put unpleasant side effects to digestion. The pregnancy hormone, progesterone, slows down the emptying of the stomach and relaxes the valve at the entrance of the stomach. This results in heartburn (or acid reflux). Try eating smaller meals and avoid eating spicy or fatty foods.It is rather common for pregnant women to suffer from bladder infections. Often, the baby places pressure on the urethra which blocks the flow of urine. If you think anything is wrong with your body, you should see medical help as soon as possible.";
        week[26]="Your uterus is now about 2.4 inches (6 cm) above your belly button. If you’ve been having a balanced diet, you should have added roughly 16 to 22 pounds. Again, remember that every pregnancy is different and do not worry if you are a few pounds off.Take this time to baby proof your home. Remember to cover all electrical outlets and block off staircases to protect your child. But no matter how much protection you put on your house, it will never be a substitute for careful parental supervision.At this point, finding a comfortable position to sleep may be very challenging. If needed, invest in a body pillow to help you get in a more comfortable position.As your baby grows in size, you do as well. This leads to back pain, headaches, leg cramps, and pressure in your pelvis. Some easy remedies involve exercise and stretching.";
        week[27]="Your uterus is about 2.75 inches (7.0 cm) above your belly button.During this trimester, your body will need an extra 300 to 350 calories a day. Your body will continue to put on weight until the 36th or 37th week. You will notice that the weight will distribute to different places. As long as you eat a healthy, well balanced diet, the weight you gain is healthy and necessary to sustain you and your baby. Remember to ask your doctor if you think you are gaining too little or too much weight.In the third trimester, it is important to recognize the signs of premature labor such as contractions of the uterus in a noticeable pattern, lower abdominal cramping, and increased pressure in your pelvis or vagina. At this point, your healthcare provider may ask you to start counting your baby’s kicks. This may range from how many times your baby moves in a hour or how long it takes for your baby to move ten times.";
        week[28]="At this point, your uterus will be a bit over 3 inches (8 cm) above your belly button. As your uterus grows, it is not uncommon to experience round ligament pain. This is usually a sharp pain in the pelvic area and is a normal symptom of pregnancy.During the third trimester, you probably want to start visiting your practitioner every 2 weeks. It is also a great time to start taking child birth classes if you haven’t already.Other symptoms of the third semester are: heartburn, hemorrhoids, constipation, urinary incontinence, swelling, and itching skin.Your breasts may also begin leaking colostrums. If they don’t do not worry. You will still be able to breastfeed if you want to.Your doctor probably sent for some blood tests early in your pregnancy. One thing that these blood tests test for is Rh factor, a substance found in blood cells. If your Rh factor doesn’t match your baby’s (one has it, the other doesn’t), there is a potential for blood problems. Your doctor can prevent this by providing a vaccine called Rh immune globin this week and again after delivery.";
        week[29]="Your uterus now sits about 2.75 to 4 inches (7.5 to 11 cm) above your belly button.You will also notice that your weight gain during this time is very rapid as your baby quickly increases in size.Remember that the irritations from pregnancy – itchy skin, visible veins, hemorrhoids, urinary incontinence, leg cramps, and many more – will pass with your delivery of the baby. With your baby, you may also feel as if your internal organs are crowded. They are. The best way to deal with these irritations is to maintain proper nutrition, exercise, drink plenty of fluids, have good posture, and rest whenever possible.Remember to maintain a nutritious diet with your prenatal vitamins and supplements.";
        week[30]="Your uterus is approximately 4 inches above your belly button.Because the hormone levels in your body are very high in order to maintain pregnancy, your body is also slowing down its digestive process.  This slowing down will result in an increase in constipation.  Increasing exercise and fiber intake will be help decrease feelings of constipation.Drinking different types of herbal teas may help with the effects the pregnancy may have on your body.  Chamomile will help with ingestion, ginger root will help with nausea, and peppermint will relieve feelings of bloating.If you are feeling uncomfortable sitting, try to be more aware of your posture.  As your baby is becoming bigger and bigger by the week, you will find yourself slouching more.  Trying to maintain proper posture will help relieve some pain.Exercising and stretching will have beneficial effects down the road as trying to remain even slightly active will increase your stamina during birth, but will also increase the amount of energy you have each day until the big day arrives.10 more weeks to go!  You can do it!";
        week[31]="Very little physical change from last week, with the exception that you breasts are beginning to produce colostrum.  Colostrum is main source of nutrition for your baby prior to the milk that may nourish your child, if you choose to breastfeed.  For this reason, colostrum is also called pre-milk.  Colostrum may be watery or thick, and may be clear or an off-white color.  A retail store will carry breast pads that you may use to protect your clothes.Back pains  may be more intense from this point onward.  A light back rub or a little heat through a heating pad may provide comfort while being safe for your baby.  Continue to be conscious of your posture and improve it when necessary as doing so will provide comfort as well.  You may experience heartburn and leg cramps as well, so be sure to continue eating, but at smaller portions to reduce heartburn, and continue to exercise to relieve and prevent frequent cramping.Sleeping may prove difficult, and understandably.  To help, try to maintain a constant sleep routine.  Refrain from drinking too many fluids before sleep, as going to the washroom may disrupt much needed rest.  Avoid caffeine as much as possible, especially after the morning.  Sleeping in a cool room will provide comfort for you baby and ensure a good night's rest for you.";
        week[32]="Now is a good time time to check with your health care provider to make sure you're going to be ready for delivery.  He or she will monitor your blood pressure, urine, and swelling, or any fluctuations in weight or unusual symptoms.This is probably a good time to start packing a hospital bag as well to be ready for the big day.  Even though the average pregnancy term is 40 weeks, there is some variation around the mean, so it's always good to be ready.Braxton-Hicks contractions, or the contractions that your body will naturally produce in order to prepare for delivery will begin by the beginning of this week.  The differences between Braxton-Hicks contractions and the true labor contractions are that the Braxton-Hicks contractions won't increase intensity and will most likely to be irregular.  Ask a health care provider if you have any other questions about pre-labor contractions and the real labor contractions.";
        week[33]="Feeling heavier?  Don't worry, it's natural to gain another pound each week until labor begins.  You may also be experiencing sciatic pain, so consult your healthcare provider about pain medication.  Do not take any medication that your healthcare provider has not approved.";
        week[34]="Fatigue, difficulty sleeping, and aches and pains may be hurdles this week, but you will be fine!Anxiety about delivery may be looming, but concentrate on your health and make beneficial decisions to get enough physical activity in as possible.  It will be helpful as the big day comes closer and closer.Here is a quick lesson labor process.  There are 3 stages of labor.  The first is to open the cervix, the second is to push the baby out through the cervix, and the third is to give birth to expel the placenta. 6 weeks left!  You're doing great!";
        week[35]="Contractions are becoming more frequent.  Rest assured, however, this is a sign that you body is still preparing for delivery.Sleeping may prove to be difficult.  Part of this is due to the physical effects of pregnancy, but another part may be due to the anxiety of caused by worrying about the health of the baby, the intensity of labor, and/or thinking about life as a parent.  Although worrying may lead you to plan ahead, too much worrying is not only detrimental to your health, but unproductive as well.  Just remember that all of these things are long processes and that you need to take things one day a time.  So try to relax as much as possible.  Reach out to someone familiar if you feel they will offer a helping hand.Limit fluids close to bedtime to decrease frequency or urination during sleep and to get avoid the backache of having to get up to use the washroom. 5 more weeks!";
        week[36]="Continue consuming healthy foods.  You should be eating somewhere between 2400-2500 calories a day.  Remember, the more nutritious foods you eat, the easier the weeks leading up to delivery will become.Most babies will be in a head-down position by this week, but if your baby's feet or tailbone has descended into the birth canal first, also known as the breach position, your healthcare provider can recommend exercises to coax the baby to turn around, making delivery easier.Be sure to inquire about what you should do if your water breaks, hospital plans, and breast feeding the next time you visit your doctor so you will be ready.  It is always beneficial to plan ahead, just don't make yourself nervous.";
        week[37]="Because almost nothing can be done to stop labor by this week, your pregnancy is considered to be carried to term.Cervical mucus in a vaginal discharge will be great because your body is greatly preparing for delivery.Run through home or hospital plans depending on where you are planning to have the birth. Invite all the people you want involved to go over the plans. It is also important to have everyone in close contact, whether it be through calling or maintaining close contact in these remaining weeks. Remember, you want as many people around as possible that are going to provide a supportive environment during labor. Keeping a cell phone with your contacts numbers, if possible, will make arranging unexpected plans easier.";
        week[38]="Frequent urination is going to be very likely from now until the time of your birth because your baby is lying deep in your pelvis, leaving your bladder compressed.This may be a good time to consult your doctor regarding circumcision, if you have not already.  The decision may depend upon religion or personal choice.  It may be helpful to consult those close to you to decide.";
        week[39]="Your uterus sits about 7 to 8 inches (16 – 20 cm) above your belly button. Your Braxton Hicks contractions may become more pronounced during this time. These contractions are often painful but do not become regular (which differentiates it from labor).A sign of labor is when your amniotic sac ruptures. This is referred to as your “water breaking.” This can be a large gush of water or a steady trickle.To prepare for labor, your baby descends into your pelvis. This is called engagement or lightening and usually occurs before labor for first time moms and during labor in subsequent births. At this point, you may feel huge and clumsy as your center of gravity switches. This will cause you to breathe easier but will make you go to the bathroom more often.";
        week[40]="Your uterus sits about 6-7 inches (16-20 cm) above your belly button.During this week, you hopefully will be going through labor. The first stage of labor works to stretch and thin your cervix by contracting your uterus at regular lengths. The second stage pushes the baby out of your body and into the vaginal canal. The third stage delivers the placenta.If you don’t give birth within a week of your due date, the doctor might recommend that you take a nonstress test. This tests the heart rate of the fetus and checks its movement to see if it is healthy.Your doctor might also induce labor by artificially rupturing the membrane and injecting hormones. If the risk of birth is too high, the doctor might perform a cesarean section delivery.";
      
        
        babyinfo[0]="During this first week, you are still on your menstrual period. At this point, your body is preparing for ovulation which releases  a new egg, the beginnings of your baby.";
        babyinfo[1]="During this first week, you are still on your menstrual period. At this point, your body is preparing for ovulation which releases  a new egg, the beginnings of your baby.";
        babyinfo[2]="Your egg will be fertilized by the sperm and, of the 46 chromosomes shared between the sperm and the egg that codes for your baby's features, only two chromosomes, known as sex chromosomes, determine the gender of the baby.  One sex chromosome comes from the egg and the other from the sperm. Because mothers have two X sex chromosomes (XX), an egg can only donate an X sex chromosome. Fathers, because they have one X sex chromosome and one Y sex chromosome, can donate either their X or Y sex chromosome.  If your child receives two X sex chromosomes, your child will be a girl.  On the other hand, if your child receives one X sex chromosome and one Y sex chromosome, your child will be a boy!";
        babyinfo[3]="Your baby was just conceived at the end of last week, but your fertilized egg has already started to undergo the process of cell division and form a blastomere. As the cells continue to divide, the egg moves to the uterus through the fallopian tubes.  The newly created cells cluster together into a small sphere and are collectively called a morula. After reaching the uterus, the morula becomes hollow and fills with liquid.  This is now called a blastocyst and will attach to the endometrium. The process of attaching to the endometrium is known as implantation, and, in addition to providing nutrients and oxygen to the blastocyst,  the endometrium will also remove any waste produced around the blastocyst to support a healthy growth.    ";
        babyinfo[4]="Average length of your baby is 1.5-2.5 mm (.05-.09 in).  One end is more rounded and will eventually become the head, and the the other end, which is more pointed, will develop into the spine.Your baby's heart is not completely formed, only about 3/4 of the way, but it will begin to beat this week!  In addition, the brain; reproductive, cardiovascular, nervous, and other major systems will begin to develop.";
        babyinfo[5]="Your baby is somewhere between 4-6 mm (0.15-0.24 in) and heartbeats are now visible through an ultrasound.  Because of the growing activity of the brain, an EEG is able to monitor its activity.Your baby's arms and legs are beginning to develop from buds on the body and blood is beginning to circulate through the body.  The body now has a distinguishable head and body, as well.  In fact, your baby is also starting to develop facial features.";
        babyinfo[6]="Your baby was just conceived at the end of last week, but your fertilized egg has already started to undergo the process of cell division and form a blastomere. As the cells continue to divide, the egg moves to the uterus through the fallopian tubes.  The newly created cells cluster together into a small sphere and are collectively called a morula. After reaching the uterus, the morula becomes hollow and fills with liquid.  This is now called a blastocyst and will attach to the endometrium. The process of attaching to the endometrium is known as implantation, and, in addition to providing nutrients and oxygen to the blastocyst,  the endometrium will also remove any waste produced around the blastocyst to support a healthy growth.           ";
        babyinfo[7]="Your baby will reach between 7-9 mm by the end of this week.Not only are the arms and legs still developing into this week, but your baby's hands are already starting to form, although they are not fully defined by this point.  Your baby's more prominent facial features, such as the eyes, nostrils, mouth, and ears are already beginning to become defined. internally, the digestive tract and lungs are continuing to develop.The umbilical cord will be completely formed by the end of this week.  This connection between mother and child will be vital for providing oxygen and nutrition to your baby while disposing of waste.";
        babyinfo[8]="Your baby is about 0.63 inches (1.6 cm) from crown to rump and weighs about 0.04 ounces (1 gram). Your baby is slowly gaining the appearance of a human. His fingers and toes are beginning to form this week (toe rays) and the arms can flex at the elbow and wrists.";
        babyinfo[9]="Your baby is now 0.90 inches (2.3 cm) long from crown to rump and weighs around 0.7 ounces (2 grams) Overall, your baby is starting look more human Toes begin to form for the baby. Gonads have also become testes (for boys) or ovaries (for girls). The baby will also spontaneously move away from contact.Your baby’s tail at the end of its spinal cord has shrunk and almost disappeared.The tip of your baby’s nose has also developed and can be seen in profile. Your baby’s digestive system also continues to develop. Its anus is forming and its intestines are growing longer.The skeleton may begin ossifying as the bones form cartilage. Your baby has also started taking its first few drinks from amniotic fluid.Your baby’s arms and legs are also now longer and straighter. His hands will be flexed at the wrist and will almost meet over the heart.";
        babyinfo[10]="The end of this week is the end of your baby’s embryonic period and the start of the fetal period. This is when the embryo begins resembling a little human At this point, your baby’s head will be over half of his/her total length as the head slowly becomes more rounded as well. The average size of a baby at this stage is 1.06-1.38 inches or 2.7-3.5 cm crown to rump length (CRL). Your baby should weight roughly 4 grams (.14 ounces). Tiny toes are forming and the eyes are largely open but will begin to fuse shut and will remain shut until weeks 25-27.External ears and an upper lip are beginning to form. Tooth buds are forming inside the mouth. Your baby’s tail has also disappeared!All your baby’s vital organs have formed and are starting to work together.Your baby has also begun breathing and he “breathes” amniotic fluid.";
        babyinfo[11]="As we mentioned before, your baby will be growing rapidly over the next 9 weeks from roughly 2 inches (5 cm) to 8 inches (20 cm). With this rapid growth, the blood vessels in the placenta are increasing in size and volume to provide the baby with more nutrients.Your baby’s head is also developing. Its ears are moving toward their final position on the sides of the head. You’ll also be amazed at the size of the baby’s head – at this stage, it accounts for about half of it’s body length The baby’s eyes are still very wide apart and the eyelids begin to fuse close temporarily. This week, the irises, the colored part of your eyes, are also developing.Your baby will also begin to grow teeth, fingernails, toenails, and hair follicles.Some of your baby’s organs are also almost complete including: the pancreas, the thyroid, and the gall bladder.";
        babyinfo[12]="Your baby is now approximately 2.5 – 3.5 inches (7 cm) long and should weigh about 12-14 grams (.49 ounces).Your baby’s brain continues to develop and will continue to grow from this point on but will maintain its current shape. The base of your baby's brain, known as the pituitary gland, is also beginning to make hormones this week.Your baby has also started developing nerves and a spinal cord which allows him/her to feel some types of pain.Fingers and toes have separated and hair and nails are starting to form.Your baby’s vocal cords are also formed this week. The kidneys also gain function as the baby starts swallowing amniotic fluid and passes it out as urine. Your baby’s digestive track is also strong enough to push through the bowels and absorb glucose.Most importantly, your baby’s heart is beginning to pump several quarts of blood daily. This can be heard with the aid of a doppler (a special listening device). The heartbeat will be very fast, like the clicking of horse hooves.The external genitalia have also developed and will being to start showing signs of either the male or female sex. Though your health care provider may guess the sex, there is still a lot of uncertainty.";
        babyinfo[13]="his is a big week for your baby. At the second trimester, your placenta has developed to provide your baby with oxygen, nutrients, and waste disposal. It also secretes the hormones progesterone and estrogen, which help keep your pregnancy steady.Your baby is also developing significantly. One thing that is apparent is the slowdown in the growth of your baby’s head in comparison to the rest of the body. Your baby’s face is also starting to look more human as its eyes are beginning to move closer together and the ears are moving to their normal positions.Internally, your baby’s intestines are migrating from the umbilical cord into their abdomen. The intestines are also developing as they form villi which helps in digestion. The liver also begins to secrete bile and the pancreas begins to secrete insulin. Your baby is also starting to practice swallowing as it takes in amniotic fluid and passes it out as urine.At this point, your baby may also be able to put a thumb in his or her mouth, although the sucking muscles are not fully developed yet. All twenty teeth have also formed and are waiting from under the gums. ";
        babyinfo[14]="Your baby weights about 1.6 ounces (45 grams) and is about 3.6 inches (9 cm) long from crown to rump.Some fine hairs, lanugo, are also developing all over your baby’s face. This hair will eventually cover your entire baby and will be shed before your baby’s birth.Your baby has also started secreting hormones of its own from its matured thyroid gland.Your baby’s body systems are also starting to work on their own. The digestive system is practicing moving food through the intestines. The renal system has started creating and eliminating urine.Your baby also continues to practice breathing the amniotic fluid through their lungs. He or she has also developed their soft nails on their fingers and toes.Your baby’s blood is also starting to form in their bone marrow and blood vessels. Their hands continue to develop as well and are becoming more functional.As the eyes and ears continue to move into place, the baby’s mouth has gone through major development. The sucking muscles are developing and the completion of the salivary glands should happen this week. The esophagus, windpipe, and larynx should all be present by the end of this week.";
        babyinfo[15]="Your baby is around 2.5 ounces (70 grams) and is about 4 inches (10.1 cm) from crown to rump. Your baby’s growth over the next few months is only going to grow faster.Your baby’s skeletal and muscle system continues to develop as your baby slowly becomes more active. Your baby’s bones harden and retain calcium really rapidly.If this is your first pregnancy, it probably is too early for you to feel any movement (you should feel movement within the next few weeks). If this is not your first pregnancy, then you may feel your baby start moving at this time.";
        babyinfo[16]="Your baby now weighs 3.9 ounces (110 grams) and measures about 4.7 inches (12 cm) from crown to rump. Your baby is growing fast which leads to you seeing an increase in weight gain.Your baby’s muscles are now developing to the point that it can hold its head erect, in a straight line, and its facial muscles can display a variety of expressions.Your baby’s nails are well formed and he or she is emptying out their bladder every 40 to 45 minutes.Your baby is also covered in lanugo and his or her ears have moved from the neck to the head";
        babyinfo[17]="Your baby weighs about 4.9 ounces (140 grams) and is about 5.1 inches (13 cm) from crown to rump.The placenta, which nourishes the fetus with nutrients and oxygen and removes wastes, is growing to accommodate your baby. It now contains thousands of blood vessels that bring nutrients and oxygen from your body to your baby's developing body.Your baby starts to form fat this week. The fat resides under the skin and is important in generating heat and maintaining a steady metabolism. At birth, 2-6% of the baby’s weight will be fat. This will help maintain a steady body temperature.The baby’s movements are becoming stronger and more frequent. Loud noises outside the uterus may also startle baby";
        babyinfo[18]="Your baby is 5.59 inches (14.2 cm) in total length and weighs about 6.7 ounces (190 g).Your baby’s ears are approaching their final position. Your baby’s eyes are also developing as they’re starting to face forward and their retinas may be able to detect light.Your baby’s bones will continue to harden and the pads for his fingers and toes are formed. They will gain the characteristic swirls and creases that form finger and toe prints.The meconium (an early fecal waste matter) will start to be collected in the intestines. If the baby is a boy, the prostate will begin developing as well.The baby’s heart is starting to build up muscle and is starting to pump out about 25 to 30 quarts of blood a day.";
        babyinfo[19]="Your baby weighs around 8 – 9 ounces (250 grams) and is about 6 inches (22 cm) long.Your baby is covered in vernix caseosa, a white and waxy substance, that protects your baby’s fragile skin from becoming chapped or scratched.Your baby’s permanent teeth buds are also forming behind the already formed milk teeth buds.f your baby is a girl, her ovaries also contain primitive egg cells. Girls are born with all the eggs that they will have in their life.f your baby is a girl, her ovaries also contain primitive egg cells. Girls are born with all the eggs that they will have in their life.Your baby also might start sleeping this week. Babies can find a “favorite” position. Usually, babies will have their chin tucked into the chest. Most babies can even dream (REM sleep)!";
        babyinfo[20]="Your baby is now 11 ounces (312 grams) and measures about 6.3 inches (16 cm). Though your baby is still small, it’s grown tremendously from that first dividing cell.Under the vernix caseosa (the protective coating), your baby’s skin is thickening and might start forming two layers, the epidermis (outer layer) and the dermis (deeper layer). The baby’s hair and nails are also continuing to grow.Your baby’s movements should be becoming stronger and stronger as ossification of his or her bones continues. You should be able to feel fluttering or quickening quite regularly. In fact, from the movements, you should be able to know if the baby is sleeping or awake.Your baby is also continuing to practice breathing. His or her hair on their scalp is beginning grow and all of their organs and structures are entering a period of growth.";
        
        babyinfo[21]="From this week on, your baby is usually measured from head to heel. Your baby should be around 10.51 inches (26.7cm) and weighs around 12.70 ounces (360 grams).Your baby’s digestive system has developed enough to start absorbing small amounts of sugar from the amniotic fluid. Most of the baby’s nourishment is still through the placenta.Your baby’s bone marrow have also developed enough to contribute to blood cell formation. Your baby’s liver and spleen were responsible for creating blood cells before. But, the liver will stop producing blood cells a few weeks before birth. The spleen stops producing blood cells around week 30.Your baby will also start to settle in the amniotic fluid near the end of this trimester. It usually chooses a head down position.Your baby’s hair is also rapidly developing with visible eyebrows, eyelashes, and hair on the scalp.";
        babyinfo[22]="Your baby is about 10.94 inches (27.8 cm) long and is about 15.17 ounces (430 grams).Your baby is developing its senses. His taste buds have started to form on the tongue. The baby's brain and the nerve endings are also developed enough so that the baby can feel the sense of touch.";
        babyinfo[23]="Your baby is around 11.38 (28.9cm) long and weighs roughly 1.10 pounds (510 grams).Your baby continues to accumulate fat and drink in amniotic fluid. Not only is baby able to drink and taste, but his or her other sense systems are maturing.The baby’s lanugo (hair) that covers their body is growing darker and their fingernails are also almost fully formed.Over the next few weeks, your baby will be gaining significant weight and will almost double in size.The ear is also starting to develop as the three main bones (hammer, anvil, and stirrup) are all starting to harden. This means the baby can listen to sounds but the bain is still developing rapidly.";
        babyinfo[24]="Your baby should be around 1.25 to 1.5 pounds (around 600 grams) and about a foot long (32 cm).Your baby’s lungs are developing to produce surfactant, a substance that keeps the air sacs in our lungs from collapsing.Your baby is starting to fill out her face and body and starting to look more and more like how she will look at birth. Most of the development at this point is in the growth of muscle, bone mass, and developing organs. At this point, a premature baby would be able to survive beyond birth with very special care in an Intensive Care Unit. A major problem for these babies is lung development.";
        babyinfo[25]="Your baby is about 14 inches (34.6cm) long and your baby weighs around 1.5 pounds (670g).Your baby’s spine is beginning to form its structures as the blood vessels of the lungs start developing. Your baby’s nostrils are also starting to open.Your baby also has fully formed fingerprints and his bones continue to harden.Some small blood vessels are also starting to form beneath your baby’s skin. As these blood vessels begin to fill with blood, the baby’s skin will take a pink appearance.";
        babyinfo[26]="Your baby is a little less than 2 pounds (907 grams) and is around 14 inches (35 cm) long.Your baby’s eyes will open soon and begin to blink. Your baby will be able to distinguish between light and dark from light that enters your uterus. Eyelashes are also growing in like the hair on his or her head.Your baby’s oil and sweat glands are also functional. Again, your baby can respond to noise and has been surrounded by the sounds of your heartbeat, digestion, and other bodily functions.Your baby will also continue to increase its body fat and develop its blood vessels. ";
        babyinfo[27]="At this point, your baby weighs roughly 2 pounds (880 grams) and measure from head to toe about 14.5 inches (38 cm).Your baby still needs time for its lungs, liver, and immune system to fully mature. Your baby’s hearing should be mature enough to recognize your voice as well as your partner’s.Your baby’s retina is starting to develop its layers as well.Your baby’s development in the brain will concentrate on the forebrain which deals with visual and auditory information.Your baby’s skin is wrinkled from floating in the liquid in your uterus.";
        babyinfo[28]="Your baby now weighs roughly 2.20 pounds (1,000 grams) and measure about 15 inches (38 cm).Your baby’s brain keeps on developing as its folds and grooves continue to expand. Your baby also continues layering on the fat as his or her hair grows.Your healthcare provider might tell you if your baby is head first, feet first, or bottom first (breech position). If your baby is in the breech position, you might need a cesarean section to deliver. Don’t worry if your baby is in the breech position now because your baby still has a few weeks to move.If your boy is a male, your boy’s testes will probably begin descending.";
        babyinfo[29]="Your baby weighs roughly 2.5 pounds (1.15 kg) and measure about 15 inches (38 cm) long.Your baby’s sensory organs are well developed and he or she is beginning to regulate his or her own temperature. The bone marrow is now completely in charge of producing red blood cells as well.Your baby’s fluttering has begun to be more active with kicks and punching. Make sure that your baby moves at least 10 times an hour. If your baby moves any less, make sure to talk to your health care provider.Your baby’s lungs are also developed enough to breathe air. The baby’s urinary track is also urinating about a half liter of urine into the amniotic fluid daily.";
        babyinfo[30]="Your baby is now 3 pounds, approximately 15.5 inches long, and is very conscious of the environment.  The lungs are working at near optimal levels.Even though your baby is waking up and sleeping in cycles, just like any other person, the baby's sleep cycle may be very different from your. Your baby can not only hear your voice, but is also able to distinguish your voice from a voice belonging to someone else. At this point, you will also know if your baby has the hiccups by the rhythmic twitching in the uterus.";
        babyinfo[31]="Your baby is ever-growing.  At approximately 16.25 inches and 3.33 pounds, your baby may feel like a ton.  Length and weight will be slowing down to prepare for delivery, but it is important to intake more and more folic acid, calcium, protein  Too much fluid may indicate inefficient swallowing, while too little fluid may be indicative of problems with the kidneys or urinary tract because of your baby may not be urinating properly or regularly.  Be sure to consult a doctor to ensure that your baby's organs are functioning properly";
        babyinfo[32]="At approximately 15.8 inches, from head to foot, and 4 pounds, your baby has increased its chances of survival greatly.  If born before this week, your child may experience problems sucking or nursing.Although your baby is now sleeping for the majority of the day, movement will increase greatly, and fetal kicks will become more frequent.Your baby is experiencing a growth of hair all over the body.  Eyebrows, eyelashes, and hair are beginning to grow.";
        babyinfo[33]="Your baby's lungs are almost completely developed.  Fat will also be deposited for the purpose of warmth and protection.  The actual color of your baby will be changing from pink to red because of the the fat deposits.The ever-developing neural structure within the brain are allowing your baby to not only listen, feel, and partially see, but is allowing for REM sleep cycles, which your baby experience often due to the sleeping the majority of the day.";
        babyinfo[34]="Your baby is approximately 17 inches and 4.75 pounds.It is important to intake more calcium this week because the baby is hardening its skeletal structure.   Your baby will be fine if you do not get enough calcium, but you will not be as your baby will be taking the calcium from your own bones to supplement the development.  So take in that extra calcium for both of you.Your baby will be set in its delivery position (either head or bottom) during this week.";
        babyinfo[35]="Your baby is approximately 18 inches long and close to 5 pounds! The baby's organs are already developed and are just being more and more defined each week.";
        babyinfo[36]="Your baby is approximately 19 inches long and weighs between 5.5-6.0 pounds.Multiple layers of fat are being added to your babies body, which will allow for better temperature regulation and to prepare for the outside environment upon delivery.As your baby moves more and more into the pelvic region, you will find that you are able to breathe more easily, although urination will become more frequent.If born at this point, your baby will live with little need of intense medical.";
        babyinfo[37]="Your baby is approximately 19-20 inches long and weighs about 6.5 pounds! Ask your healthcare provider about in which position you can expect to deliver your child, and if there is anything that can be done if your baby is to be born in the breech positio";
        babyinfo[38]="Your baby is approximately 20 inches long and weighs 6.5-7.0 pounds! Believe it or not, your baby has taken a bowel movement.  The waste is called meconium and is stored in the intestines.Your baby is practicing breathing and urinating, and can use the fingers to grasp very tightly onto your hands.";
        babyinfo[39]="Your baby is now approximately 7.25 pounds (3.3 kg) and is about 20 inches long (51 cm).Most of the vernix and the lanugo that has covered your baby has disappeared.Your baby also has a steady supply of antibodies from the placenta that you provided that will help it fight off infections for the first few months after birth.Your baby will also continue to gain weight, although at a slower pace. Movements will also begin to slow as it doesn’t have much room to move around.At this point, all organ systems are developed and your baby is just building layers of fat to help control its body temperature";
        babyinfo[40]="A baby born at week 40, weighs on average 7 pounds (3,300 grams) and is about 20 inches long (51 cm).Your baby might be covered with vernix, blood, and some skin discolorations. Your baby’s genitals may also appear enlarged due to your hormones in the baby’s system. These variations are completely normal and should disappear in a few days.After birth, the mucus will be suctioned out of your baby’s mouth and you will hear the baby’s first cry.";
          
        
      //deleting if exist and then adding data to local db
        data.deleteweek_info();
                      
        for(int i=0;i<week.length;i++)
        {
        	data.InsertWeekInfo(week[i], babyinfo[i], "N");
        }
        
        
        //Add symptom data...................................................
        symptom=new String[37];
        symptom_descri=new String[37];
        
        symptom[0]="Acid reflux";
        symptom[1]="Acne";
        symptom[2]="Back Pain";
        symptom[3]="BLoating";
        symptom[4]="BP Increase";
        symptom[5]="Breast Tenderness";
        symptom[6]="Crvical thinning";
        symptom[7]="Constipation";
        symptom[8]="Contractions";
        symptom[9]="Cramps";
        symptom[10]="Diarrhea";
        symptom[11]="Dizziness";
        symptom[12]="Fainting";
        symptom[13]="Fatigue";
        symptom[14]="Food Aversion";
        symptom[15]="Food Cravings";
        symptom[16]="Headache";
        symptom[17]="HeartBurn";
        symptom[18]="Hemmorrhoids";
        symptom[19]="Indigestion";
        symptom[20]="Itching";
        symptom[21]="Leg Cramps";
        symptom[22]="Mood swings";
        symptom[23]="Morning sickness ";
        symptom[24]="Nasal Congestion";
        symptom[25]="Nausea";
        symptom[26]="Nipple soreness";
        symptom[27]="Nosebleed";
        symptom[28]="Numbness/tingling";
        symptom[29]="Rib pain";
        symptom[30]="Spotting";
        symptom[31]="Stretch marks";
        symptom[32]="Swelling";
        symptom[33]="Thrush Urination";
        symptom[34]="Vaginal bleeding";
        symptom[35]="Varicose veins";
        symptom[36]="Vomiting";
        
        
        symptom_descri[0]="Acid reflux";
        symptom_descri[1]="Acne";
        symptom_descri[2]="Back Pain";
        symptom_descri[3]="BLoating";
        symptom_descri[4]="BP Increase";
        symptom_descri[5]="Breast Tenderness";
        symptom_descri[6]="Crvical thinning";
        symptom_descri[7]="Constipation";
        symptom_descri[8]="Contractions";
        symptom_descri[9]="Cramps";
        symptom_descri[10]="Diarrhea";
        symptom_descri[11]="Dizziness";
        symptom_descri[12]="Fainting";
        symptom_descri[13]="Fatigue";
        symptom_descri[14]="Food Aversion";
        symptom_descri[15]="Food Cravings";
        symptom_descri[16]="Headache";
        symptom_descri[17]="HeartBurn";
        symptom_descri[18]="Hemmorrhoids";
        symptom_descri[19]="Indigestion";
        symptom_descri[20]="Itching";
        symptom_descri[21]="Leg Cramps";
        symptom_descri[22]="Mood swings";
        symptom_descri[23]="Morning sickness ";
        symptom_descri[24]="Nasal Congestion";
        symptom_descri[25]="Nausea";
        symptom_descri[26]="Nipple soreness";
        symptom_descri[27]="Nosebleed";
        symptom_descri[28]="Numbness/tingling";
        symptom_descri[29]="Rib pain";
        symptom_descri[30]="Spotting";
        symptom_descri[31]="Stretch marks";
        symptom_descri[32]="Swelling";
        symptom_descri[33]="Thrush Urination";
        symptom_descri[34]="Vaginal bleeding";
        symptom_descri[35]="Varicose veins";
        symptom_descri[36]="Vomiting";
        
        
        
        
        
        //deleting if exist and then adding data to local db
        data.deletesymptom();
        
                
        for(int i=0;i<symptom.length;i++)
        {
        	data.InsertSymptom(symptom[i], symptom_descri[i], "N");
        }
        
        
        
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        
        
        if(mysession.getweightunit().equals(""))
        {
        	mysession.setweightunit("kg");
        }
        if(mysession.getheightunit().equals(""))
        {
        	mysession.setheightunit("in");
        }
        if(mysession.getgallery_type().equals(""))
        {
        	mysession.setgallery_type("CoveredFlow");
        }
        mysession.setreturnflag("Pan_Mom");
        
        btnregister = (Button) findViewById(R.id.btnregister);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        chkischeck=(CheckBox) findViewById(R.id.chkremember);    
        edtuname=(EditText) findViewById(R.id.edtuname);
        edtpwd=(EditText) findViewById(R.id.edtpwd);
        
       
        
        this.icon.setVisibility(View.GONE);      
        
       
        
        
        btnregister.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(Pan_MomActivity.this, Register_user.class);
				startActivity(i);
				
				
				
			}
		});
     
        btnlogin.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
							
				checkValidate();
				
			}
		});
        
             
    }  
    
    /*Type :Function
    name:checkValidate
    return type:void
    date:12-12-2011
    purpose:Checking whether user has remembered and then calling the respective intents*/
    public void checkValidate() 
    {
    	
    	 
    	
    	if((edtuname.getText().toString()).equals(""))
    	{
    		Toast.makeText(this,"User name not entered!!",Toast.LENGTH_LONG).show();
    	}
    	else if((edtpwd.getText().toString()).equals(""))
    	{
    		Toast.makeText(this,"Password not entered!!",Toast.LENGTH_LONG).show();
    	}
    	else
    	{
    		String s123=edtpwd.getText().toString();
    		
    	    try 
    	    {
    	        MessageDigest md5 = MessageDigest.getInstance("MD5");
    	        md5.update(s123.getBytes(),0,s123.length());
    	        signature = new BigInteger(1,md5.digest()).toString(16);
    	       
    	    } 
    	    catch (NoSuchAlgorithmException e)
    	    {
    	        e.printStackTrace();
    	    }
            
    		//Cursor cur=data.getlogin_details(edtuname.getText().toString(), edtpwd.getText().toString());
    		strpwd=signature;
    	    Cursor cur=data.getlogin_details(edtuname.getText().toString(),strpwd);
    		while(cur.moveToNext())
    		{
    			userid=cur.getInt(0);
    			String data=cur.getString(5);
    			System.out.println("DATA IS..."+data);	
    		}
    		int l=cur.getCount(); 
    		cur.close();  
    		
    		if(l==0)
    		{
    			internet=checkInternetConnection();
    			status=true;
    			if(internet==false)
    			{
    				Toast.makeText(Pan_MomActivity.this, "Please turn on Internet connection", Toast.LENGTH_LONG).show();    			
    			}
    			else
    			{
    		      	 uname	=edtuname.getText().toString(); 
			    	 String pass=edtpwd.getText().toString(); 
			    	 String md5string=pass;
			    	 
			    	 try 
			    	    {
			    	        MessageDigest md5 = MessageDigest.getInstance("MD5");
			    	        md5.update(md5string.getBytes(),0,s123.length());
			    	        signature = new BigInteger(1,md5.digest()).toString(16);
			    	       
			    	    } 
			    	    catch (NoSuchAlgorithmException e)
			    	    {
			    	        e.printStackTrace();
			    	    }
			    	// sss=signature;
			    	    sss="$P$BwDpmlstUloUgaEsuatNAOND9d5Imo."; 
			    	 System.out.println("password ************"+pass);
			    	 System.out.println("password ************"+sss);
			    	 ArrayList<NameValuePair> nameValuePairs23 = new ArrayList<NameValuePair>();
				       	nameValuePairs23.add(new BasicNameValuePair("username",uname));
				       	nameValuePairs23.add(new BasicNameValuePair("password",sss));
				     	//nameValuePairs23.add(new BasicNameValuePair("userurl11",strurl));
				        try
				         {
				                HttpClient httpclient = new DefaultHttpClient();
				                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/login.php");
				                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs23));
				                HttpResponse response = httpclient.execute(httppost);
				                HttpEntity entity = response.getEntity();
				                is = entity.getContent();      
				              
				         }
				         catch(Exception e) 
				         { 
				        	 
				         }   
				       	
				         try
				         {
				         	BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				         	StringBuilder sb = new StringBuilder();
				       
				         	String line ;
				      
				         	while ((line = reader.readLine())!= null) 
				         	{
				         		sb.append(line + "\n");				         		
				         		System.out.println("line is :"+line);
				         	}
				         	is.close();
				         	result=sb.toString();
				         	System.out.println("result   :"+result);
				         }
				         catch(Exception e)
			 	         { 				     	   
				         }
				         try
		  		          {
		  			       int temp1;      
		  			             JSONArray jArray = new JSONArray(result);		  			            		  			            
		  			             	for(int i=0;i<jArray.length();i++)
		  			             	{
		  			             			JSONObject json_data = jArray.getJSONObject(i);
		  			             			temp1 = json_data.getInt("ID");
		  			             			ret_uname = json_data.getString("user_login");
				             				ret_pass=json_data.getString("user_pass");	 
				             				ret_id=Integer.toString(temp1);
		  			             	}
		  			             	
		  			             	System.out.println("retrived_id  ret_id ="+ret_id);
	                       		    System.out.println("retrived_id  ret_uname ="+ret_uname);
	                       		  System.out.println("retrived_id  ret_pass ="+ret_pass);
		  			       }
		  			      	catch(JSONException e)
		  			      	{
		  			           			//text1.setText("Invalid Login!!"); 
		  			      	}
		  			      System.out.println("check  uname ="+uname);
                 		    System.out.println("check  ret_uname ="+ret_uname);
                 		   System.out.println("check  sss ="+sss);
                 		  System.out.println("check  ret_pass ="+ret_pass);
				         if(uname.equals(ret_uname) && sss.equals(ret_pass))
				         {
				        	 login();  
				         status=false;
				         }
				         else
				         {
				        	 Toast.makeText(this,"Invalid  Login !!",Toast.LENGTH_LONG).show();
				        	 }
    			} //////////////////////////////////////////////////////////////////////////////////////////////
    			if(chkischeck.isChecked())
		        {
    				mysession.setmainpage_flag("true");
		        	mysession.storeuserdetails(Integer.toString(userid),edtuname.getText().toString(), strpwd);
		        	mysession.setflag("true");
		        	System.out.println("FLAG IS SET TO TRUE....");
		        	
		        }
    			
    			//Cursor c=data.check_due_date(edtuname.getText().toString(), edtpwd.getText().toString());
    			Cursor c=data.check_due_date(edtuname.getText().toString(), strpwd);
    			while(c.moveToNext())
    			{
    				strdue_date=c.getString(0);
    				System.out.println("DUE DATE IS..."+strdue_date);	
    			}
    			
    			c.close();
    			
    			
    				try
    				{
    					if(strdue_date.equalsIgnoreCase(null))
    	    			{
    						System.out.println("IN TRY IF!!!!!!!!!");
    	      			}
    					else
    					{
    						System.out.println("IN TRY ELSE!!!!!!!!!");
    						Intent i=new Intent(Pan_MomActivity.this, Dashboard.class);
    						Bundle bun=new Bundle();
    						bun.putString("UNAME",edtuname.getText().toString());
    						//bun.putString("PWD",edtpwd.getText().toString());
    						bun.putString("PWD",strpwd);
    						bun.putInt("USERID", userid);
    						i.putExtras(bun);
        	    			startActivity(i);
    						
    					}
    				}
    				catch (Exception e) {
						// TODO: handle exception
    					System.out.println("IN CATCH!!!!!!!!!");
    					Intent i=new Intent(Pan_MomActivity.this, Enter_due_date.class);
    					Bundle bun=new Bundle();
						bun.putString("UNAME",edtuname.getText().toString());
						
						System.out.println("PANOMOM:UNAME:"+edtuname.getText().toString());
						//bun.putString("PWD",edtpwd.getText().toString());
						bun.putString("PWD",strpwd);
						bun.putString("RETURNFLAG", return_flag);
						bun.putInt("USERID", userid);
						i.putExtras(bun);
            			startActivity(i);
					}
    				
    			
    			
    			
    		}  
    		else
    		{    			
    			if(chkischeck.isChecked())
		        {
    				mysession.setmainpage_flag("true");
		        	mysession.storeuserdetails(Integer.toString(userid),edtuname.getText().toString(), strpwd);
		        	mysession.setflag("true");
		        	System.out.println("FLAG IS SET TO TRUE....");
		        	
		        }
    			
    			//Cursor c=data.check_due_date(edtuname.getText().toString(), edtpwd.getText().toString());
    			Cursor c=data.check_due_date(edtuname.getText().toString(), strpwd);
    			while(c.moveToNext())
    			{
    				strdue_date=c.getString(0);
    				System.out.println("DUE DATE IS..."+strdue_date);	
    			}
    			
    			c.close();
    			
    			
    				try
    				{
    					if(strdue_date.equalsIgnoreCase(null))
    	    			{
    						System.out.println("IN TRY IF!!!!!!!!!");
    	      			}
    					else
    					{
    						System.out.println("IN TRY ELSE!!!!!!!!!");
    						Intent i=new Intent(Pan_MomActivity.this, Dashboard.class);
    						Bundle bun=new Bundle();
    						bun.putString("UNAME",edtuname.getText().toString());
    						//bun.putString("PWD",edtpwd.getText().toString());
    						bun.putString("PWD",strpwd);
    						bun.putInt("USERID", userid);
    						i.putExtras(bun);
        	    			startActivity(i);
    						
    					}
    				}
    				catch (Exception e) {
						// TODO: handle exception
    					System.out.println("IN CATCH!!!!!!!!!");
    					Intent i=new Intent(Pan_MomActivity.this, Enter_due_date.class);
    					Bundle bun=new Bundle();
						bun.putString("UNAME",edtuname.getText().toString());
						
						System.out.println("PANOMOM:UNAME:"+edtuname.getText().toString());
						//bun.putString("PWD",edtpwd.getText().toString());
						bun.putString("PWD",strpwd);
						bun.putString("RETURNFLAG", return_flag);
						bun.putInt("USERID", userid);
						i.putExtras(bun);
            			startActivity(i);
					}			
    			}
    			//Toast.makeText(this,"Invalid Login!!",Toast.LENGTH_LONG).show();
    		}    	    	
    	}
    
    /*Type :Function
    name:login
    return type:void
    date:12-12-2011
    purpose:Checking whether login details available on server*/
    public void login()
    {
    	ArrayList<NameValuePair> nameValuePairs22 = new ArrayList<NameValuePair>();
       	nameValuePairs22.add(new BasicNameValuePair("userid",ret_id));
       System.out.println("in login  "+ret_id);
        try
         {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/retriveddata_register.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs22));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is1 = entity.getContent();      
              
         }
         catch(Exception e) 
         { 
        	 
         }   
         try
         {
         	BufferedReader reader = new BufferedReader(new InputStreamReader(is1,"iso-8859-1"),8);
         	StringBuilder sb = new StringBuilder();
       
         	String line ;
      
         	while ((line = reader.readLine())!= null) 
         	{
         		sb.append(line + "\n");				         		
         		System.out.println("line is :"+line);
         	}
         	is1.close();
         	result=sb.toString();
         	System.out.println("result   :"+result);
         }
         catch(Exception e)
	         { 				     	   
         }
         try
	          {
		       int temp1;      
		             JSONArray jArray = new JSONArray(result);		  			            		  			            
		             	for(int i=0;i<jArray.length();i++)
		             	{
		             			JSONObject json_data = jArray.getJSONObject(i);
		             			
		             			ret_nicename = json_data.getString("user_nicename");
             				ret_email=json_data.getString("user_email");	 
             				ret_date=json_data.getString("user_registered");	 
		             	}
		             	
		             	System.out.println("retrived_id  ret_nicename ="+ret_nicename);
           		    System.out.println("retrived_id  ret_email ="+ret_email);
           		  System.out.println("retrived_id  ret_date ="+ret_date);
		       }
		      	catch(JSONException e)
		      	{
		           			//text1.setText("Invalid Login!!"); 
		      	}
   /////////////////////////////////////////////////////////////////////////////////////////////////////////  
		      	//ret_id="2";
		      	ArrayList<NameValuePair> nameValuePairs6 = new ArrayList<NameValuePair>();
		       	nameValuePairs6.add(new BasicNameValuePair("userid",ret_id));
		       System.out.println("in login  "+ret_id);
		        try
		         {
		                HttpClient httpclient = new DefaultHttpClient();
		                HttpPost httppost = new HttpPost("http://bpsi.us/blueplanetsolutions/panmom/ret_xp_profile.php");
		                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs6));
		                HttpResponse response = httpclient.execute(httppost);
		                HttpEntity entity = response.getEntity();
		                is3 = entity.getContent();      
		              
		         }
		         catch(Exception e) 
		         { 
		        	 
		         }   
		         try
		         {
		         	BufferedReader reader = new BufferedReader(new InputStreamReader(is3,"iso-8859-1"),8);
		         	StringBuilder sb = new StringBuilder();
		       
		         	String line ;
		      
		         	while ((line = reader.readLine())!= null) 
		         	{
		         		sb.append(line + "\n");				         		
		         		System.out.println("line is :"+line);
		         	}
		         	is3.close();
		         	result=sb.toString();
		         	System.out.println("result   :"+result);
		         }
		         catch(Exception e)
			         { 				     	   
		         }
		         try
			          {
				             
				             JSONArray jArray = new JSONArray(result);		  			              		  			            
				             	for(int i=0;i<jArray.length();i++)
				             	{
				             			JSONObject json_data = jArray.getJSONObject(i);
				             			
				             			values[i]= json_data.getString("value");
		             				
				             	}
				             	ret_name=values[0];
				             	ret_lmp=values[1];
				             	ret_duedate=values[2];
				             	ret_age=values[3];
				             	ret_occ_self=values[4];
				             	ret_occ_hus=values[5];
				             	ret_mobs=values[6];
				             	ret_mobh=values[7];
				             	ret_mobg=values[8];
				             	
				             	System.out.println("retrived_id  ret_name ="+ret_name);
		           		    System.out.println("retrived_id  ret_lmp ="+ret_lmp);
		           		  System.out.println("retrived_id  ret_duedate ="+ret_duedate);
		           		System.out.println("retrived_id  ret_age ="+ret_age);
		           		System.out.println("retrived_id  ret_occ_self ="+ret_occ_self);
		           		System.out.println("retrived_id  ret_occ_hus ="+ret_occ_hus);
		           		System.out.println("retrived_id  ret_mobs ="+ret_mobs);
		           		System.out.println("retrived_id  ret_mobh ="+ret_mobh);
		           		System.out.println("retrived_id  ret_mobg ="+ret_mobg);
				             	 	arr_due_date=new String[3];    
				         if(ret_lmp.equals("")||ret_lmp.equals(" "))
				         {
				        	 arr_due_date=ret_duedate.split("/");

				        	int dpm=Integer.parseInt(arr_due_date[0].trim());
				        	int dpd=Integer.parseInt(arr_due_date[1].trim());
				        	int dpy=Integer.parseInt(arr_due_date[2].trim());
				        	
				           	Calendar thatDay = Calendar.getInstance();
			    		       thatDay.set(Calendar.DAY_OF_MONTH,dpd);
			    		       thatDay.set(Calendar.MONTH,dpm); // 0-11 so 1 less
			    		       thatDay.set(Calendar.YEAR,dpy);	
			    		       
			    		       thatDay.add(Calendar.DAY_OF_MONTH,-7);
			    		        thatDay.add(Calendar.MONTH,-9);
			    		        
			    		        int s11=thatDay.get(Calendar.MONTH);
			    		        int s21=thatDay.get(Calendar.DAY_OF_MONTH);
			    		        int s31=thatDay.get(Calendar.YEAR);
			    		       
			    		       

			    	        ret_lmp=s21+"-"+(s11)+"-"+s31;
				         }
	////////////////////////////////////////////////////			         
				         if(ret_duedate.equals("")||ret_duedate.equals(" "))
				         {
				        	 arr_due_date=ret_lmp.split("/");

				        	int dpm=Integer.parseInt(arr_due_date[0].trim());
				        	int dpd=Integer.parseInt(arr_due_date[1].trim());
				        	int dpy=Integer.parseInt(arr_due_date[2].trim());
				        	
				        	Calendar thatDay = Calendar.getInstance();
			    		       thatDay.set(Calendar.DAY_OF_MONTH,dpd);
			    		       thatDay.set(Calendar.MONTH,dpm); // 0-11 so 1 less
			    		       thatDay.set(Calendar.YEAR,dpy);	
			    		       
			    		       thatDay.add(Calendar.DAY_OF_MONTH,+7);
			    		        thatDay.add(Calendar.MONTH,+9);
			    		        
			    		        int s11=thatDay.get(Calendar.MONTH);
			    		        int s21=thatDay.get(Calendar.DAY_OF_MONTH);
			    		        int s31=thatDay.get(Calendar.YEAR);
			    		       
			    		       

			    		        ret_duedate=s21+"-"+(s11)+"-"+s31;
				         }
				         
				         
		           		  int pan_id;
		           		  pan_id=Integer.parseInt(ret_id);
 data.Insertregister(ret_date,ret_name, ret_email,sss, ret_nicename,ret_lmp,ret_duedate,ret_age, ret_occ_self,ret_occ_hus,ret_mobs,ret_mobh,ret_mobg,pan_id);
 Cursor cur=data.getlogin_details(edtuname.getText().toString(),strpwd);
	while(cur.moveToNext())
	{
		userid=cur.getInt(0);
		String s1=cur.getString(1);
		String s2=cur.getString(2);
		String s3=cur.getString(3);
		String s4=cur.getString(4);
		String data=cur.getString(5);
		String s5=cur.getString(6);
		String s6=cur.getString(7);
		System.out.println("DATA IS..."+data);	
		System.out.println("DATA IS..."+s1);
		System.out.println("DATA IS..."+s2);
		System.out.println("DATA IS..."+s3);      
		System.out.println("DATA IS..."+s4);
		System.out.println("DATA IS..."+s5);
		System.out.println("DATA IS..."+s6);
		
	}		         
				       }
				      	catch(JSONException e)
				      	{
				           			//text1.setText("Invalid Login!!"); 
				      	}
         
    }
    /*Type :Function
    name:checkInternetConnection
    return type:void
    date:12-12-2011
    purpose:Checking whether internet connection is available or not*/
    private boolean checkInternetConnection() {
		   ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
		   if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() &&    conMgr.getActiveNetworkInfo().isConnected()) {
		         return true;
		   } else {
		         System.out.println("Internet Connection Not Present");
		       return false;
		   }
		}
    //overriding devices back button
    @Override 
	public void onBackPressed()
	{
		System.out.println("BACK BUTTON PRESSED!!!!");
	}
    
    
}