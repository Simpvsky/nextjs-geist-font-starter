# How to Publish to GitHub

1. Create a new repository on GitHub:
   - Go to github.com and sign in
   - Click the "+" button in the top right and select "New repository"
   - Name it "wearos-monitor-app"
   - Leave it public
   - Don't initialize with README (we already have one)
   - Click "Create repository"

2. Open terminal in the wearos-monitor-app directory and run these commands:
```bash
git init
git add .
git commit -m "Initial commit: Wear OS fitness monitoring app"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/wearos-monitor-app.git
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username.

The project is already set up with a proper .gitignore file for Android development, so you can safely commit all files.
