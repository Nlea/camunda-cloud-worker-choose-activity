package io.camunda.cloudworkerchooseactivity;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.response.Topology;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Array;
import java.util.Random;

@SpringBootApplication
@EnableZeebeClient
public class CloudWorkerChooseActivityApplication {

	@Autowired
	private ZeebeClientLifecycle client;
	Logger logger = LoggerFactory.getLogger(CloudWorkerChooseActivityApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CloudWorkerChooseActivityApplication.class, args);
	}

	@ZeebeWorker(type ="choose-activity")
	public void handleChooseActivity(final JobClient client, final ActivatedJob job){
		logger.info(job.toString());
		String[] activities = {"\"Get a newspaper or a journal and spend some time to read an interesting article that is not related to your work (of course you can also read an online article)\"",
				"\"spend some time today to listen to a podcast.\"", "\"put on your running shoes and go running",
				"\"spend some time today in the downward facing dog or any other inverted position: the world looks different if you turn it upside down \"",
				"\"choose a recipe you have never cooked before: cook it today or plan a day to cook it soon, so you can buy all the ingredients \"",
		"\"spend some time to read some pages in the book you are currently reading or start reading one\"",
				"\"kitchen party: mix your favourite cocktail (or mix a cocktail with the ingredients you can find in your kitchen\"",
				"\"call a friend\"", "\"during your next walk, walk some steps backwards\"", "\"ice cream time: get your favourite icecream today\"",
				"\"do some yoga,\"", "\"even if it is not christmas bake some cookies or make a plan to do that soon\"",
				"\"backing-time: take some care of your oven and bake a cake, bread or veggies\"", "\"take some time today to practice/ learn a language\"",
				"\"go to the Camunda Forum and answer a question\"", "\"Spa Day: buy a facial mask\"", "\"order food from your favourite restaurant\"",
				"\"repair something in your flat/house\"", "\"paint your nails all in a different colour, if you don't have nail polish use a pen and paper to draw it\"",
				"\"red wine\"", "\"beer\"", "\"listen music very loud\"", "\"dance in your living room like everyone is watching\"",
				"\"draw a picture\"", "\"go biking, is it raining? No worries you will be back home afterwards so no excuse for getting wet\"",
				"\"write a letter (yes a actually letter) to someone\"", "\"play a game (alone or online with your friends)\"",
				"\"wear your favourite head today\"", "\"play Zoom Bingo\"", "\"buy some flowers today\"",
				"\"take a look at your photos, what is your favourite photo you took within the last 6 months (maybe you like to print it)\"" ,
				"\"make someone a surprise or plan to make the surprise soon (maybe with a little gift)\"",
				"\"select one item in your flat/ house that you don't need anymore. If it is something useful give it away, if not throw it out\"",
				"\"go to the supermarket and try to buy something you have never eaten/ cooked before and try it out\"",
				"\"charity time: donate something today (time, money or things)\"", "\"Push up day: how many push ups can you do within the next 24 hours?\"",
				"\"do the jumping jack for 5 minutes\"", "\"handmade: try to spend some time on a DYI project, if you don't have one maybe start one\"",
				"\"send someone a postcard, if you can't buy one, be creative and try to make one\"", "\"take a break from social media today\"",
				"\"movie time: select a movie and prepare a cinema evening in your flat/ house with popcorn ... you might even sale tickets to your household members\"" ,
				"\"organize a MeetUp for the people in your household, decide on a topic to discuss for the evening and make everyone prepare something for it\"",
				"\"exchange cloth with someone in your household for the day\"", "\"Karaoke time: turn on a Karaoke song and sing it!\"",
				"\"light up some candles tonight\"", "\"Dress up for as you want to go to a fancy party\"",
				"\"Take some time and write down three good things that happened within the last week \"","\"Play a computer game\""};

		Random rand = new Random();
		int upperbound = activities.length;
		int int_random = rand.nextInt(upperbound);
		System.out.println("Number: "+ int_random + " von " + activities.length);



		String activity = activities[int_random];

		client.newCompleteCommand(job.getKey())
				.variables(("{\"activity\":" + activity + "}"))
				.send()
				.join();
		}

	}



