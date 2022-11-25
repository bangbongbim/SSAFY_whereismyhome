function makeDateStr(year, month, day, type) {
  // type + / or . (ex)2022/7/24
  return year + type + (month < 10 ? "0" + month : month) + type + (day < 10 ? "0" + day : day);
}

function makePaginationHtml(
  listRowCount,
  pageLinkCount,
  currentPageIndex,
  totalListItemCount,
  htmlTargetId
) {
  var targetUI = document.querySelector("#" + htmlTargetId);

  // 총 페이지 카운트 계산 (총 게시글 / 한번에 보여주는 row 개수)
  var pageCount = Math.ceil(totalListItemCount / listRowCount);

  var startPageIndex = 0;
  if (currentPageIndex % pageLinkCount == 0) {
    // 10, 20...맨마지막
    // 현재 페이지 index가 20 일 때, 11로
    // 20 - 10 => 10, 10 + 1 => 11
    startPageIndex = currentPageIndex - pageLinkCount + 1;
  } else {
    // 현재 페이지 index가 23 일 때, 21로
    // 23 / 10 => 2.3 => 2, 2*10 => 20, 20+1 => 21
    startPageIndex = Math.floor(currentPageIndex / pageLinkCount) * pageLinkCount + 1;
  }

  var endPageIndex = 0;
  if (currentPageIndex % pageLinkCount == 0) {
    // 10, 20...맨마지막
    endPageIndex = currentPageIndex;
  } else {
    endPageIndex = Math.floor(currentPageIndex / pageLinkCount) * pageLinkCount + pageLinkCount;
  }

  var prev;
  if (currentPageIndex <= pageLinkCount) {
    prev = false;
  } else {
    prev = true;
  }

  var next;
  // 127 건 / 10 => 12.7 => 13 <-- pageCount
  // 위 계산이 13 보다 크면 13으로
  if (endPageIndex > pageCount) {
    endPageIndex = pageCount;
    next = false;
  } else {
    next = true;
  }

  var paginationHtml = '<ul class="pagination pagination-sm justify-content-center">';
  // 이전 페이지 리스트
  if (prev) {
    paginationHtml +=
      '<li class="page-item">' +
      '<a class="page-link" href="javascript:movePage(' +
      (startPageIndex - 1) +
      ')" aria-label="Previous">' +
      '<span aria-hidden="true">&laquo;</span>' +
      "</a>" +
      "</li>";
  }

  // 중간 (숫자로 보이는) 페이지 리스트
  for (var i = startPageIndex; i <= endPageIndex; i++) {
    // console.log('*** ' + i);
    // console.log(currentPageIndex);

    if (i == currentPageIndex) {
      // 현재 페이지이면 active한다.
      paginationHtml +=
        '<li class="page-item active"><a class="page-link" href="javascript:movePage(' +
        i +
        ')">' +
        i +
        "</a></li>";
    } else {
      paginationHtml +=
        '<li class="page-item"><a class="page-link" href="javascript:movePage(' +
        i +
        ')">' +
        i +
        "</a></li>";
    }
  }

  // 다음 페이지 리스트
  if (next) {
    paginationHtml +=
      '<li class="page-item">' +
      '<a class="page-link" href="javascript:movePage(' +
      (endPageIndex + 1) +
      ')" aria-label="Next">' +
      '<span aria-hidden="true">&raquo;</span>' +
      "</a>" +
      "</li>";
  }

  paginationHtml += "</ul>";

  targetUI.innerHTML = paginationHtml;
}
