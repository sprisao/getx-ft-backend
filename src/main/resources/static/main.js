document.getElementById("naverLoginBtn").addEventListener("click", function() {
    console.log("네이버 로그인 시도");

    const clientId = "2aIpUgp68uEYugBfJ44f";
    const redirectUri = encodeURIComponent("http://localhost:8080/login/oauth2/code/naver");
    const state = generateRandomString();  // 랜덤 문자열 생성 함수 호출
    const naverAuthUrl = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}&state=${state}`;

    window.location.href = naverAuthUrl;
});

function generateRandomString() {
    const array = new Uint32Array(28);
    window.crypto.getRandomValues(array);
    return Array.from(array, dec => ('0' + dec.toString(16)).substr(-2)).join('');
}
