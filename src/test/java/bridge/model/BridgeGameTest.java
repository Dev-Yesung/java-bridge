package bridge.model;

import bridge.dto.GameResultDto;
import bridge.dto.MoveCommandDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BridgeGameTest {
	@DisplayName("게임 생성자 테스트") @Test public void initializeBridgeTest() {
		BridgeGame bridgeGame = new BridgeGame();
		bridgeGame.initializeBridge("3");
		System.out.println(bridgeGame);
		Assertions.assertThat(bridgeGame.toString()).contains("다리의 길이 : 3");
	}

	@DisplayName("이동 테스트") @Test public void moveTest() {
		BridgeGame bridgeGame = new BridgeGame();

		bridgeGame.initializeBridge("3");
		System.out.println(bridgeGame + "\n");
		MoveCommandDto moveCommandDto = bridgeGame.move("U");
		System.out.println(bridgeGame + "\n");

		Assertions.assertThat(moveCommandDto.getMoveCommand()).isEqualTo("U");
		Assertions.assertThat(moveCommandDto.getMoveFlag()).isInstanceOf(Boolean.class);
	}

	@DisplayName("게임 재시작 테스트") @Test public void retryTest1() {
		BridgeGame bridgeGame = new BridgeGame();
		bridgeGame.initializeBridge("3");
		GameResultDto gameResultDto = bridgeGame.retry("R");
		Assertions.assertThat(gameResultDto.getTotalTry()).isEqualTo(2);
		Assertions.assertThat(gameResultDto.getSuccessFlag()).isFalse();
	}

	@DisplayName("게임 종료 테스트") @Test public void retryTest2() {
		BridgeGame bridgeGame = new BridgeGame();
		bridgeGame.initializeBridge("3");
		GameResultDto gameResultDto = bridgeGame.retry("Q");
		Assertions.assertThat(gameResultDto.getTotalTry()).isEqualTo(1);
		Assertions.assertThat(gameResultDto.getSuccessFlag()).isFalse();
	}

	@DisplayName("게임 클리어 테스트") @Test public void clearTest1() {
		MoveCommandDto moveCommandDto = new MoveCommandDto("", true, false);
		BridgeGame bridgeGame = new BridgeGame();
		bridgeGame.initializeBridge("3");

		for (int i = 0; i < 3; i++) {
			moveCommandDto = bridgeGame.move("U");
			if (!moveCommandDto.getMoveFlag()) {
				moveCommandDto = bridgeGame.move("D");
			}
		}

		System.out.println(bridgeGame);

		Assertions.assertThat(moveCommandDto.getMoveCommand()).isNotEqualTo("");
		Assertions.assertThat(moveCommandDto.getMoveFlag()).isNotEqualTo(false);
		Assertions.assertThat(moveCommandDto.getMoveCommand()).containsAnyOf("U", "D");
		Assertions.assertThat(moveCommandDto.getGameClear()).isTrue();
	}

	@DisplayName("게임 클리어 실패 테스트") @Test public void clearTest2() {
		MoveCommandDto moveCommandDto = new MoveCommandDto("", true, false);
		BridgeGame bridgeGame = new BridgeGame();
		bridgeGame.initializeBridge("3");

		// 다리 값이 UUU가 나올 때 빼고 성공
		for (int i = 0; i < 3; i++) {
			moveCommandDto = bridgeGame.move("U");
		}

		System.out.println(bridgeGame);

		Assertions.assertThat(moveCommandDto.getMoveCommand()).isNotEqualTo("");
		Assertions.assertThat(moveCommandDto.getMoveFlag()).isEqualTo(false);
		Assertions.assertThat(moveCommandDto.getMoveCommand()).containsAnyOf("U", "D");
		Assertions.assertThat(moveCommandDto.getGameClear()).isFalse();
	}
}
