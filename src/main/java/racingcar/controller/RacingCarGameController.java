package racingcar.controller;

import java.util.ArrayList;
import java.util.List;
import racingcar.model.Car;
import racingcar.model.Referee;
import racingcar.util.Parser;
import racingcar.util.Random;
import racingcar.util.validator.InputValidator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class RacingCarGameController {

    private InputView inputView;
    private OutputView outputView;
    private List<Car> cars = new ArrayList<>();

    public RacingCarGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        createCars(inputView.getCarNames());

        Referee referee = createReferee();

        racing(referee);

        printWinners(referee);
    }

    private void createCars(String inputCarNames) {
        checkInputCarNames(inputCarNames);
        List<String> carNames = Parser.parseStringToList(inputCarNames);
        for (String carName : carNames) {
            cars.add(new Car(carName));
        }
    }

    private void racing(Referee referee) {
        outputView.printNewline();
        outputView.printResultMessage();

        carsMovementProgress(referee);
    }

    private Referee createReferee() {
        return new Referee(inputView.getNumberOfRound());
    }

    private void printWinners(Referee referee) {
        outputView.printWinners(referee.judgementWinnerCars(cars));
    }

    private void checkInputCarNames(String inputCarNames) {
        InputValidator.checkEmpty(inputCarNames);
        InputValidator.checkDuplicate(inputCarNames);
    }

    private void carsMovementProgress(Referee referee) {
        for (int i=0; i < referee.getRoundNumber(); i++) {
            carsMovement(cars);
            outputView.printAllCarMovement(cars);
            outputView.printNewline();
        }
    }

    private void carsMovement(List<Car> cars) {
        for (Car car : cars) {
            car.move(Random.createRandomNumber());
        }
    }

}
