/**
 * v-dialogDragWidth Can pull the width of the window.（on the right side.）
 * Copyright (c) 2019 ruoyi
 */

export default {
  bind(el) {
    const dragDom = el.querySelector('.el-dialog');
    const lineEl = document.createElement('div');
    lineEl.style = 'width: 5px; background: inherit; height: 80%; position: absolute; right: 0; top: 0; bottom: 0; margin: auto; z-index: 1; cursor: w-resize;';
    lineEl.addEventListener('mousedown',
      function (e) {
        // Press the mouse.，Calculate the distance of the current element from the visible area
        const disX = e.clientX - el.offsetLeft;
        // Current width
        const curWidth = dragDom.offsetWidth;
        document.onmousemove = function (e) {
          e.preventDefault(); // Prohibit default events when moving.
          // Committed by the incident.，Calculate the distance.
          const l = e.clientX - disX;
          dragDom.style.width = `${curWidth + l}px`;
        };
        document.onmouseup = function (e) {
          document.onmousemove = null;
          document.onmouseup = null;
        };
      }, false);
    dragDom.appendChild(lineEl);
  }
}
