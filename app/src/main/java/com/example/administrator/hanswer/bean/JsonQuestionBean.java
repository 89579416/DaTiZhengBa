package com.example.administrator.hanswer.bean;

import com.example.administrator.hanswer.db.tbl_question;

/**
 * status : ok
 * code : 200
 * Created by K on 2018/6/28 0028.
 */

public class JsonQuestionBean {
    private String status;
    private String code;
    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result extends tbl_question {
        private int id;
        private String type;
        private String title;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private static String answer;
        private static String write_answer;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getWrite_answer() {
            return write_answer;
        }

        public void setWrite_answer(String write_answer) {
            this.write_answer = write_answer;
        }
    }

//    public List<QuestionBean> getMessage() {
//        return message;
//    }
//
//    public void setMessage(List<QuestionBean> message) {
//        this.message = message;
//    }

    @Override
    public String toString() {
        return "JsonQuestionBean {" + "status=' " + status + '\'' + ", code=' " + code + '\'' + ", result=" + result + '}';
    }
}
