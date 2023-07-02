package com.ant.hurry.boundedContext.board.controller;

import com.ant.hurry.base.rq.Rq;
import com.ant.hurry.base.rsData.RsData;
import com.ant.hurry.boundedContext.board.dto.CreateConvertDTO;
import com.ant.hurry.boundedContext.board.dto.CreateRequest;
import com.ant.hurry.boundedContext.board.entity.Board;
import com.ant.hurry.boundedContext.board.entity.BoardType;
import com.ant.hurry.boundedContext.board.entity.TradeType;
import com.ant.hurry.base.region.entity.Region;
import com.ant.hurry.base.region.service.RegionSearchService;
import com.ant.hurry.boundedContext.board.service.BoardService;
import com.ant.hurry.boundedContext.member.entity.Member;
import com.ant.hurry.boundedContext.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final Rq rq;
    private final RegionSearchService regionService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String showCreateBoard(Model model) {
        model.addAttribute("boardTypes", BoardType.values());
        model.addAttribute("tradeTypes", TradeType.values());
        return "board/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createBoard(@Valid CreateRequest createRequest, BindingResult bindingResult, Model model) {
        model.addAttribute("boardTypes", BoardType.values());
        model.addAttribute("tradeTypes", TradeType.values());
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "board/create";
        }
        if (createRequest.getAddress().isBlank()) {
            return rq.historyBack("주소를 입력해주세요.");
        }
        RsData checkUserCoin = boardService.hasEnoughCoin(createRequest.getRewardCoin());
        if (checkUserCoin.isFail()) {
            return rq.historyBack(checkUserCoin);
        }
        CreateConvertDTO boardInfo = boardService.addressConvert(createRequest);
        RsData<Board> boardRs = boardService.write(rq.getMember(), boardInfo);
        return rq.redirectWithMsg("/board/enterRegion?code=" + boardInfo.getRegCode(), boardRs);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String showBoard(Model model, @PathVariable("id") Long id) {
        Board board = boardService.findById(id).orElse(null);
        if (board == null) {
            return rq.historyBack("존재하지 않는 게시판 입니다.");
        }
        model.addAttribute("board", board);
        return "/board/board";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable("id") Long id) {
        RsData<Board> canDeleteBoard = boardService.canDelete(rq.getMember(), id);
        if (canDeleteBoard.isFail()) {
            return rq.historyBack(canDeleteBoard);
        }
        RsData<Board> deleteBoard = boardService.delete(id);
        return rq.redirectWithMsg("/board/selectRegion", deleteBoard);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String showModify(@PathVariable Long id, Model model) {

        RsData<Board> canModifyBoard = boardService.canModify(rq.getMember(), id);
        if (canModifyBoard.isFail()) {
            return rq.historyBack(canModifyBoard);
        }

        model.addAttribute("board", canModifyBoard.getData());
        model.addAttribute("boardTypes", BoardType.values());
        model.addAttribute("tradeTypes", TradeType.values());

        return "board/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, @Valid CreateRequest createRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "board/modify";
        }


        RsData<Board> modifyBoard = boardService.modify(id, createRequest, rq.getMember());
        if (modifyBoard.isFail()) {
            return rq.historyBack(modifyBoard);
        }


        return rq.redirectWithMsg("/board/" + id, modifyBoard);
    }


    /**
     * 지역 검색하면 나오는 지역 리스트를 보여준다.
     **/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/selectRegion")
    public String showRegion(Model model) {
        List<Region> regions = regionService.findAll();
        model.addAttribute("regions", regions);
        return "board/selectRegion";
    }


    /**
     * selectRegion에서 지역을 선택하면 해당 지역의 게시판으로 이동한다.
     **/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/enterRegion")
    public String enterRegion(@RequestParam("code") String code, Model model) {
        Region region = regionService.findByCode(code).orElseThrow();

        List<Board> board1 = boardService.findByCodeAndBoard(code, BoardType.나급해요);
        List<Board> board2 = boardService.findByCodeAndBoard(code, BoardType.나잘해요);


        model.addAttribute("region", region);
        model.addAttribute("board1", board1);
        model.addAttribute("board2", board2);
        return "board/enterRegion";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/online")
    public String showOnlineBoard(Model model) {
        List<Board> board = boardService.findByTradeTypeAndBoardType(TradeType.온라인, BoardType.나급해요);

        model.addAttribute("board", board);
        return "board/online";
    }
}

