package GUI_3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PaymentApi {

    private static final String BASE_URL =
            "http://localhost:8080/api/payments";

    private static final HttpClient client =
            HttpClient.newHttpClient();

    // 가상 결제 요청
    public static String createPayment(String username, int amount)
            throws Exception {

        String json = String.format(
                "{\"username\":\"%s\",\"amount\":%d}",
                username,
                amount
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }


    // 가상 결제 승인 요청
    public static String approvePayment(long paymentId)
            throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + paymentId + "/approve"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    // 사용자별 결제 내역 조회
    public static String getPayments(String username)
            throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" +
                        java.net.URLEncoder.encode(
                                username,
                                java.nio.charset.StandardCharsets.UTF_8
                        )))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }


    // 가상 결제 취소 요청
    public static String cancelPayment(long paymentId)
            throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + paymentId + "/cancel"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
    public static String formatPayment(String json) {

        String orderId = extract(json, "orderId");
        String username = extract(json, "username");
        String amount = extract(json, "amount");
        String status = extract(json, "status");
        String id = extract(json, "id");

        String statusText = status;

        if ("PENDING".equals(status)) {
            statusText = "승인 대기";
        } else if ("SUCCESS".equals(status)) {
            statusText = "승인 완료";
        } else if ("CANCELED".equals(status)) {
            statusText = "취소 완료";
        }

        int price = Integer.parseInt(amount);

        return "주문 번호 : " + orderId
                + "\n사용자 : " + username
                + "\n충전 금액 : " + String.format("%,d", price)
                + " 가상 칩"
                + "\n결제 상태 : " + statusText
                + "\n결제 ID : " + id;
    }

    private static String extract(String json, String key) {

        java.util.regex.Pattern pattern =
                java.util.regex.Pattern.compile(
                        "\"" + key + "\"\\s*:\\s*(?:\"([^\"]*)\"|([^,}]+))"
                );

        java.util.regex.Matcher matcher =
                pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1) != null
                    ? matcher.group(1)
                    : matcher.group(2).trim();
        }

        return "";
    }

    public static String formatPayments(String json) {

        java.util.regex.Pattern pattern =
                java.util.regex.Pattern.compile("\\{[^{}]*\\}");

        java.util.regex.Matcher matcher =
                pattern.matcher(json);

        StringBuilder result = new StringBuilder();

        int count = 0;

        while (matcher.find()) {

            count++;

            result.append("========== 결제 내역 ")
                .append(count)
                .append(" ==========\n");

            result.append(formatPayment(matcher.group()))
                .append("\n\n");
        }

        if (count == 0) {
            return "조회된 결제 내역이 없습니다.";
        }

        return result.toString();
    }

}
