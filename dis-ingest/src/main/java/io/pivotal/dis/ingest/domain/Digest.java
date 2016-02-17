package io.pivotal.dis.ingest.domain;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Digest {
    private List<DisruptedLine> disruptions;

    public Digest(List<DisruptedLine> disruptions) {
        this.disruptions = disruptions;
    }

    public List<DisruptedLine> getDisruptions() {
        return disruptions;
    }

    public Optional<String> getStartTimeFromDisruptedLine(String lineName) {
        return getFieldFromDisruptedLine(lineName, DisruptedLine::getStartTime);
    }

    public Optional<String> getEndTimeFromDisruptedLine(String lineName) {
        return getFieldFromDisruptedLine(lineName, DisruptedLine::getEndTime);
    }

    public Optional<String> getEarliestEndTimeFromDisruptedLine(String lineName) {
        return getFieldFromDisruptedLine(lineName, DisruptedLine::getEarliestEndTime);
    }

    public Optional<String> getLatestEndTimeFromDisruptedLine(String lineName) {
        return getFieldFromDisruptedLine(lineName, DisruptedLine::getLatestEndTime);
    }

    private Optional<String> getFieldFromDisruptedLine(String lineName,
                                                       Function<DisruptedLine, String> fieldExtractor) {

        if (isLineDisrupted(lineName)) {
            DisruptedLine line = getLine(lineName).get();
            return Optional.of(fieldExtractor.apply(line));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Long> getLongFieldFromDisruptedLine(String lineName,
                                                       Function<DisruptedLine, Long> fieldExtractor) {

        if (isLineDisrupted(lineName)) {
            DisruptedLine line = getLine(lineName).get();
            return Optional.of(fieldExtractor.apply(line));
        } else {
            return Optional.empty();
        }
    }

    private boolean isLineDisrupted(String lineName) {
        return disruptions.stream()
                .anyMatch(disruptedLine -> disruptedLine.getLine().equals(lineName));
    }

    private Optional<DisruptedLine> getLine(String lineName) {
        return getDisruptions().stream()
                .filter(disruptedLine -> disruptedLine.getLine().equals(lineName))
                .findFirst();
    }

    public Optional<Long> getStartTimestampFromDisruptedLine(String lineName) {
        return getLongFieldFromDisruptedLine(lineName, DisruptedLine::getStartTimestamp);
    }

    public Optional<Long> getEndTimestampFromDisruptedLine(String lineName) {
        return getLongFieldFromDisruptedLine(lineName, DisruptedLine::getEndTimestamp);
    }

    public Optional<Long> getEarliestEndTimestampFromDisruptedLine(String lineName) {
        return getLongFieldFromDisruptedLine(lineName, DisruptedLine::getEarliestEndTimestamp);
    }

    public Optional<Long> getLatestEndTimestampFromDisruptedLine(String lineName) {
        return getLongFieldFromDisruptedLine(lineName, DisruptedLine::getLatestEndTimestamp);
    }
}
