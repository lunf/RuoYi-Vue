## Development

```bash
# The Clone Project
git clone https://gitee.com/y_project/RuoYi-Vue

# Enter the project catalogue.
cd ruoyi-ui

# Installed dependence.
npm install

# It is recommended not to use directly. cnpm Installed dependence.，There will be some strange. bug。can be solved through the following operations. npm Slow speed problem.
npm install --registry=https://registry.npmmirror.com

# Start the service.
npm run dev
```

Browser access http://localhost:80

## Published

```bash
# Building a test environment.
npm run build:stage

# Building a production environment
npm run build:prod
```