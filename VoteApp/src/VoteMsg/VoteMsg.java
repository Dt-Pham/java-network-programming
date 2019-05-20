package VoteMsg;

import java.io.IOException;

public class VoteMsg {
    private boolean isResponse;
    private boolean isInquiry;
    private int candidateID;
    private int voteCount;

    private static int MAX_CANDIDATE_ID = 1000;

    public VoteMsg(boolean isResponse, boolean isInquiry, int candidateID, int voteCount) throws IllegalArgumentException {
        if (!isResponse && voteCount != 0) {
            throw new IllegalArgumentException("Request vote count must be zero");
        }
        if (voteCount < 0) {
            throw new IllegalArgumentException("VoteCount count must be zero");
        }
        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
            throw new IllegalArgumentException("Bad candidate ID");
        }

        this.isResponse = isResponse;
        this.isInquiry = isInquiry;
        this.candidateID = candidateID;
        this.voteCount = voteCount;
    }

    public void setResponse(boolean isResponse) {
        this.isResponse = isResponse;
    }

    public void setInquiry(boolean isInquiry) {
        this.isInquiry = isInquiry;
    }

    public boolean isReponse() {
        return isResponse;
    }

    public boolean isInquiry() {
        return isInquiry;
    }

    public void setCandidateID(int candidateID) {
        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
            throw new IllegalArgumentException("Bad candidate ID");
        }
        this.candidateID = candidateID;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setVoteCount(int voteCount) {
        if (voteCount < 0 || (voteCount != 0 && !isResponse)) {
            throw new IllegalArgumentException("Bad vote count number: " + voteCount);
        }
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        String res = (isInquiry ? "Inquiry" : "Vote") + " for candidate " + candidateID;
        if (isResponse) {
            res = "Response to " + res + " which now have " + voteCount + " vote(s).";
        }
        return res;
    }

}