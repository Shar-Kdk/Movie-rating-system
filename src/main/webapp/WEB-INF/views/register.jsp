<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Register - Movie Rating System</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary-color: #5968e2;
      --primary-hover: #4353d0;
      --text-color: rgba(255, 255, 255, 0.95);
      --text-muted: rgba(255, 255, 255, 0.7);
      --bg-dark: #111111;
      --bg-card: rgba(25, 25, 35, 0.65);
      --border-color: rgba(255, 255, 255, 0.12);
      --input-bg: rgba(255, 255, 255, 0.08);
      --input-focus-bg: rgba(255, 255, 255, 0.12);
      --shadow-color: rgba(0, 0, 0, 0.4);
      --success-color: #4CAF50;
      --error-color: #f44336;
      --transition: all 0.3s ease;
    }

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
      line-height: 1.6;
      color: var(--text-color);
      background: linear-gradient(135deg, #1a1a2e, #16213e);
      height: 100vh;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      position: relative;
      background-attachment: fixed;
      overflow-y: auto;
    }

    .auth-page::before {
      content: '';
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: url('https://source.unsplash.com/random/1920x1080/?movie,cinema') no-repeat center center;
      background-size: cover;
      filter: brightness(0.25) blur(8px);
      z-index: -1;
    }

    .auth-wrapper {
      max-width: 480px;
      width: 100%;
      padding: 15px;
      margin: 20px auto;
      z-index: 10;
    }

    .auth-container {
      background: var(--bg-card);
      border-radius: 16px;
      box-shadow: 0 12px 32px var(--shadow-color);
      backdrop-filter: blur(16px);
      -webkit-backdrop-filter: blur(16px);
      border: 1px solid var(--border-color);
      padding: 30px;
      transition: var(--transition);
    }

    .auth-header {
      text-align: center;
      margin-bottom: 24px;
    }

    .auth-logo {
      width: 60px;
      height: 60px;
      background: linear-gradient(45deg, var(--primary-color), #7580ee);
      border-radius: 16px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 0 auto 12px;
      box-shadow: 0 6px 16px rgba(89, 104, 226, 0.3);
    }

    .auth-logo i {
      font-size: 28px;
      color: white;
    }

    .auth-header h1 {
      font-size: 24px;
      font-weight: 700;
      margin-bottom: 4px;
      letter-spacing: -0.01em;
    }

    .auth-header p {
      font-size: 15px;
      font-weight: 400;
      color: var(--text-muted);
    }

    .auth-form {
      margin-top: 16px;
    }

    .single-field-row {
      margin-bottom: 16px;
    }

    .form-group {
      position: relative;
      width: 100%;
    }

    .input-group {
      position: relative;
    }

    .input-icon {
      position: absolute;
      left: 14px;
      top: 50%;
      transform: translateY(-50%);
      color: var(--text-muted);
      font-size: 16px;
      transition: var(--transition);
      pointer-events: none;
    }

    input[type="text"],
    input[type="email"],
    input[type="password"] {
      width: 100%;
      padding: 12px 12px 12px 40px;
      background: var(--input-bg);
      border: 1px solid var(--border-color);
      border-radius: 10px;
      font-size: 15px;
      font-weight: 400;
      color: var(--text-color);
      transition: var(--transition);
      outline: none;
    }

    input:focus {
      background: var(--input-focus-bg);
      border-color: var(--primary-color);
      box-shadow: 0 0 0 3px rgba(89, 104, 226, 0.2);
    }

    input:focus + .input-icon {
      color: var(--primary-color);
    }

    input::placeholder {
      color: var(--text-muted);
      opacity: 0.7;
    }

    .error-message {
      display: flex;
      align-items: center;
      gap: 8px;
      background: rgba(244, 67, 54, 0.1);
      border-left: 3px solid var(--error-color);
      color: #ff8a80;
      padding: 10px 12px;
      border-radius: 8px;
      margin-bottom: 16px;
      font-size: 14px;
    }

    .error-message i {
      color: var(--error-color);
    }

    /* File input styling */
    .file-input-wrapper {
      border: 2px dashed var(--border-color);
      border-radius: 10px;
      padding: 14px;
      text-align: center;
      transition: var(--transition);
      background: var(--input-bg);
      position: relative;
      cursor: pointer;
    }

    .file-input-wrapper:hover {
      border-color: var(--primary-color);
      background: var(--input-focus-bg);
    }

    .file-input-label {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 6px;
      color: var(--text-color) !important;
      font-weight: 500;
      margin-bottom: 6px;
      cursor: pointer;
    }

    .file-input-label i {
      font-size: 24px;
      color: var(--primary-color) !important;
      margin-bottom: 2px;
    }

    .file-name {
      font-size: 14px;
      color: var(--text-muted) !important;
      transition: var(--transition);
    }

    .file-name.has-file {
      color: var(--primary-color) !important;
      font-weight: 500;
    }

    input[type="file"] {
      display: block;
      width: 100%;
      opacity: 0;
      position: absolute;
      top: 0;
      left: 0;
      height: 100%;
      cursor: pointer;
    }

    .btn {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      padding: 12px 20px;
      font-size: 15px;
      font-weight: 600;
      text-align: center;
      border-radius: 10px;
      cursor: pointer;
      transition: var(--transition);
      border: none;
      outline: none;
      text-decoration: none;
    }

    .btn-block {
      width: 100%;
    }

    .btn-primary {
      background: var(--primary-color);
      color: white;
      box-shadow: 0 4px 12px rgba(89, 104, 226, 0.3);
    }

    .btn-primary:hover {
      background: var(--primary-hover);
      transform: translateY(-2px);
      box-shadow: 0 6px 16px rgba(89, 104, 226, 0.4);
    }

    .btn-primary:active {
      transform: translateY(0);
    }

    .btn-outline {
      background: transparent;
      color: var(--text-color);
      border: 1px solid var(--border-color);
    }

    .btn-outline:hover {
      background: rgba(255, 255, 255, 0.1);
      border-color: var(--text-muted);
    }

    .auth-divider {
      display: flex;
      align-items: center;
      gap: 16px;
      margin: 24px 0;
      color: var(--text-muted);
      font-size: 14px;
    }

    .auth-divider::before,
    .auth-divider::after {
      content: "";
      flex: 1;
      height: 1px;
      background: var(--border-color);
    }

    .auth-links {
      margin-top: 12px;
    }

    .auth-links a {
      color: var(--text-color);
      text-decoration: none;
    }

    .password-toggle-link {
      position: absolute;
      right: 14px;
      top: 50%;
      transform: translateY(-50%);
      font-size: 15px;
      color: var(--text-muted);
      text-decoration: none;
      transition: var(--transition);
    }

    .password-toggle-link:hover {
      color: var(--primary-color);
    }

    .single-row {
      margin-bottom: 16px;
    }

    .back-home {
      position: fixed;
      top: 15px;
      left: 15px;
      color: var(--text-color);
      text-decoration: none;
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 14px;
      font-weight: 500;
      padding: 10px 14px;
      border-radius: 10px;
      background: rgba(255, 255, 255, 0.08);
      backdrop-filter: blur(16px);
      -webkit-backdrop-filter: blur(16px);
      border: 1px solid var(--border-color);
      transition: var(--transition);
      z-index: 100;
    }

    .back-home:hover {
      background: rgba(255, 255, 255, 0.15);
      transform: translateX(-3px);
    }

    .back-home i {
      font-size: 14px;
    }

    /* Animations */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .auth-container {
      animation: fadeIn 0.4s ease-out forwards;
    }

    .auth-logo, .auth-header h1, .auth-header p {
      opacity: 0;
      animation: fadeIn 0.4s ease-out forwards;
    }

    .auth-logo { animation-delay: 0.1s; }
    .auth-header h1 { animation-delay: 0.15s; }
    .auth-header p { animation-delay: 0.2s; }
    .auth-form { animation-delay: 0.25s; opacity: 0; animation: fadeIn 0.4s ease-out 0.25s forwards; }

    /* Responsive styles */
    @media (max-height: 700px) {
      .auth-wrapper {
        margin: 10px auto;
      }

      .auth-container {
        padding: 20px;
      }

      .auth-header {
        margin-bottom: 15px;
      }

      .single-field-row {
        margin-bottom: 12px;
      }

      .auth-logo {
        width: 50px;
        height: 50px;
      }
    }

    @media (max-width: 576px) {
      .auth-wrapper {
        padding: 10px;
      }

      .auth-container {
        padding: 20px;
        border-radius: 12px;
      }

      .back-home {
        top: 10px;
        left: 10px;
        padding: 8px 12px;
        font-size: 13px;
      }

      input[type="text"],
      input[type="email"],
      input[type="password"] {
        padding: 11px 11px 11px 36px;
      }

      .btn {
        padding: 11px 16px;
      }
    }
  </style>
</head>
<body class="auth-page">
<a href="${pageContext.request.contextPath}/" class="back-home">
  <i class="fas fa-arrow-left"></i>
  <span>Back to Home</span>
</a>

<div class="auth-wrapper">
  <div class="auth-container">
    <div class="auth-header">
      <div class="auth-logo">
        <i class="fas fa-film"></i>
      </div>
      <h1>Create Account</h1>
      <p>Join our movie rating community</p>
    </div>

    <% if(request.getAttribute("error") != null) { %>
    <div class="error-message">
      <i class="fas fa-exclamation-circle"></i>
      <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form
            action="${pageContext.request.contextPath}/register"
            method="post"
            class="auth-form register-form"
            enctype="multipart/form-data"
    >
      <!-- One input per row -->
      <div class="single-field-row">
        <div class="form-group">
          <div class="input-group">
              <span class="input-icon">
                <i class="fas fa-user"></i>
              </span>
            <input
                    type="text"
                    id="name"
                    name="name"
                    placeholder="Enter your full name"
                    value="<%= session.getAttribute("formName") != null ? session.getAttribute("formName") : "" %>"
                    required
            />
          </div>
        </div>
      </div>

      <div class="single-field-row">
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
                    value="<%= session.getAttribute("formEmail") != null ? session.getAttribute("formEmail") : "" %>"
                    required
            />
          </div>
        </div>
      </div>

      <div class="single-field-row">
        <div class="form-group">
          <div class="input-group">
              <span class="input-icon">
                <i class="fas fa-lock"></i>
              </span>
            <input
                    type="<%= session.getAttribute("passwordVisible") != null && (Boolean)session.getAttribute("passwordVisible") ? "text" : "password" %>"
                    id="password"
                    name="password"
                    placeholder="Create a password"
                    required
            />
            <a href="${pageContext.request.contextPath}/register/togglePassword?field=password" class="password-toggle-link">
              <i class="fas fa-<%= session.getAttribute("passwordVisible") != null && (Boolean)session.getAttribute("passwordVisible") ? "eye-slash" : "eye" %>"></i>
            </a>
          </div>
        </div>
      </div>

      <div class="single-field-row">
        <div class="form-group">
          <div class="input-group">
              <span class="input-icon">
                <i class="fas fa-lock"></i>
              </span>
            <input
                    type="<%= session.getAttribute("confirmPasswordVisible") != null && (Boolean)session.getAttribute("confirmPasswordVisible") ? "text" : "password" %>"
                    id="confirmPassword"
                    name="confirmPassword"
                    placeholder="Confirm password"
                    required
            />
            <a href="${pageContext.request.contextPath}/register/togglePassword?field=confirmPassword" class="password-toggle-link">
              <i class="fas fa-<%= session.getAttribute("confirmPasswordVisible") != null && (Boolean)session.getAttribute("confirmPasswordVisible") ? "eye-slash" : "eye" %>"></i>
            </a>
          </div>
        </div>
      </div>

      <div class="single-row">
        <div class="form-group">
          <label for="profileImage" class="file-input-wrapper">
            <div class="file-input-label">
              <i class="fas fa-camera"></i>
              <span>Profile Image (Optional)</span>
            </div>
            <input
                    type="file"
                    id="profileImage"
                    name="profileImage"
                    accept="image/*"
            />
            <div class="file-name <%= session.getAttribute("uploadedFileName") != null ? "has-file" : "" %>">
              <%= session.getAttribute("uploadedFileName") != null ? session.getAttribute("uploadedFileName") : "No file chosen" %>
            </div>
          </label>
        </div>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn btn-primary btn-block">
          Create Account
        </button>
      </div>

      <div class="auth-divider">
        <span>Already have an account?</span>
      </div>

      <div class="auth-links">
        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline btn-block">
          <i class="fas fa-sign-in-alt"></i>
          Sign In
        </a>
      </div>
    </form>
  </div>
</div>
</body>
</html>