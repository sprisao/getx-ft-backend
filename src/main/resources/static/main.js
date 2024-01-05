document.getElementById("naverLoginBtn").addEventListener("click", function() {
    const clientId = "2aIpUgp68uEYugBfJ44f"; // 네이버 클라이언트 ID
    const redirectUri = encodeURIComponent("http://localhost:8080/login/oauth2/code/naver"); // 백엔드 redirectUri
    const state = "RANDOM_STRING"; // CSRF 방지를 위한 상태 토큰
    const naverAuthUrl = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}&state=${state}`;

    window.location.href = naverAuthUrl;
});
