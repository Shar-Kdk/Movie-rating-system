<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Login - Movie Rating System</title>

  <!-- External CSS -->
  <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css"
  />

  <!-- Font Awesome for icons -->
  <link
          rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
  />

  <style>
    /* Fixed Back to Home Button */
    .back-home {
      position: fixed;
      top: 20px;
      left: 20px;
      color: rgba(255, 255, 255, 0.9);
      text-decoration: none;
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 16px;
      padding: 10px 15px;
      border-radius: 8px;
      background: rgba(255, 255, 255, 0.1);
      backdrop-filter: blur(10px);
      -webkit-backdrop-filter: blur(10px);
      border: 1px solid rgba(255, 255, 255, 0.1);
      transition: all 0.3s ease;
    }

    .back-home:hover {
      background: rgba(255, 255, 255, 0.2);
      transform: translateX(-3px);
    }

    .back-home i {
      font-size: 18px;
    }

    @media (max-width: 576px) {
      .back-home {
        top: 10px;
        left: 10px;
        padding: 8px 12px;
        font-size: 14px;
      }
    }

    /* Error message style */
    .error-message {
      color: #ff4d4d;
      background-color: #ffe5e5;
      padding: 10px;
      margin: 15px 0;
      border-left: 4px solid #ff4d4d;
      border-radius: 4px;
      display: flex;
      align-items: center;
      gap: 10px;
      font-size: 14px;
    }

    .error-message i {
      font-size: 18px;
    }

    /* Password toggle button inside input */
    .password-toggle {
      background: none;
      border: none;
      color: #999;
      cursor: pointer;
      position: absolute;
      right: 10px;
      top: 50%;
      transform: translateY(-50%);
      font-size: 16px;
    }

    /* Password toggle link */
    .password-toggle-link {
      position: absolute;
      right: 10px;
      top: 50%;
      transform: translateY(-50%);
      font-size: 16px;
      color: #999;
      text-decoration: none;
    }

    .password-toggle-link:hover {
      color: #fff;
    }
  </style>
</head>

<body class="auth-page">
<!-- Back to Home Button -->
<a href="${pageContext.request.contextPath}/" class="back-home">
  <i class="fas fa-arrow-left"></i>
  <span>Back to Home</span>
</a>

<!-- Login Form Container -->
<div class="auth-wrapper">
  <div class="auth-container">
    <div class="auth-header">
      <div class="auth-logo">
        <i class="fas fa-film"></i>
      </div>
      <h1>Welcome Back</h1>
      <p>Sign in to your account to continue</p>
    </div>

    <!-- Error Message (If exists) -->
    <% if (request.getAttribute("error") != null) { %>
    <div class="error-message">
      <i class="fas fa-exclamation-circle"></i>
      <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <!-- Login Form -->
    <form
            action="${pageContext.request.contextPath}/login"
            method="post"
            class="auth-form"
    >
      <!-- Email Field -->
      <div class="form-group">
        <div class="input-group">
              <span class="input-icon">
                <i class="fas fa-envelope"></i>
              </span>
          <input
                  type="email"
                  id="email"
                  name="email"
                  placeholder="Enter your email"
                  value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                  required
          />
        </div>
      </div>

      <!-- Password Field with Toggle -->
      <div class="form-group">
        <div class="input-group" style="position: relative;">
              <span class="input-icon">
                <i class="fas fa-lock"></i>
              </span>
          <input
                  type="<%= session.getAttribute("passwordVisible") != null && (Boolean)session.getAttribute("passwordVisible") ? "text" : "password" %>"
                  id="password"
                  name="password"
                  placeholder="Enter your password"
                  required
          />
          <a href="${pageContext.request.contextPath}/login/togglePassword" class="password-toggle-link">
            <i class="fas fa-<%= session.getAttribute("passwordVisible") != null && (Boolean)session.getAttribute("passwordVisible") ? "eye-slash" : "eye" %>"></i>
          </a>
        </div>
      </div>

      <!-- Remember Me Option -->
      <div class="form-options">
        <label class="remember-me">
          <input type="checkbox" name="remember" id="remember" />
          <span class="checkmark"></span>
          Remember me
        </label>
      </div>

      <!-- Sign In Button -->
      <div class="form-actions">
        <button type="submit" class="btn btn-primary btn-block">
          Sign In
        </button>
      </div>

      <!-- Divider and Register Link -->
      <div class="auth-divider">
        <span>Don't have an account?</span>
      </div>

      <div class="auth-links">

        href="${pageContext.request.contextPath}/register"
        class="btn btn-outline btn-block"
        >
        <i class="fas fa-user-plus"></i>
        Create Account
        </a>
      </div>
    </form>
  </div>
</div>
</body>
</html>