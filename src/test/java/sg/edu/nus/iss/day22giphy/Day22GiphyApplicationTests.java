package sg.edu.nus.iss.day22giphy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.nus.iss.day22giphy.services.GiphyService;

@SpringBootTest
class Day22GiphyApplicationTests {

	@Autowired
	private GiphyService gSvc;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturn5img() throws IOException{
		List<String> testImg = gSvc.getSearchResults("pokemon", 5, "g");
		assertEquals(5, testImg.size(), "number of photos should be 5");
	}

}
