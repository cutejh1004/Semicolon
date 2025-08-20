package com.Semicolon.cmnt.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Semicolon.cmnt.command.BoardModifyCommand;
import com.Semicolon.cmnt.command.BoardRegistCommand;
import com.Semicolon.cmnt.command.PageMaker;
import com.Semicolon.cmnt.dto.BoardVO;
import com.Semicolon.cmnt.dto.Recruit_BoardVO;
import com.Semicolon.cmnt.service.BoardService;
import com.Semicolon.cmnt.service.Recruit_BoardService;
import com.josephoconnell.html.HTMLInputFilter;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private BoardService BoardService;
	@Autowired
	private Recruit_BoardService recruit_boardservice;
	
	@Resource(name="summernotePath")
	private String summernotePath;

	
	@GetMapping("/main")
	public void main() {}
	
	@GetMapping("/Board")
	public void Board(@ModelAttribute PageMaker pageMaker, Model model) throws Exception {
		List<BoardVO> boardList = BoardService.list(pageMaker);		
		model.addAttribute("boardList",boardList);
	}
	
	@GetMapping("/Recruit")
	public void Recruit(@ModelAttribute PageMaker pageMaker, Model model) throws Exception {
		List<Recruit_BoardVO> boardList = recruit_boardservice.list(pageMaker);		
		model.addAttribute("boardList",boardList);
	}
	
	@GetMapping("/detail")
	public void detail(int fno, HttpServletRequest request,Model model)throws Exception{
		
		ServletContext ctx = request.getServletContext();
		
		String key = "board:"+fno;
		
		BoardVO board = null;
		
		if(ctx.getAttribute(key)!=null) {
			board = BoardService.getBoard(fno);
		}else {
			ctx.setAttribute(key, key);
			board = BoardService.detail(fno);
		}	

		model.addAttribute("board",board);
	}
	
	@GetMapping("/regist")
	public void registForm() {}
	
	@PostMapping("/regist")
	public String registPost(BoardRegistCommand regCommand)throws Exception{
		String url = "/community/regist_success";
		
		Recruit_BoardVO board = regCommand.toRecruit_Board();
		board.setTitle(HTMLInputFilter.htmlSpecialChars(board.getTitle()));
		
		recruit_boardservice.regist(board);
		
		return url;
	}
	
	@GetMapping("/modify")
	public void modifyForm(int fno, Model model)throws Exception{
		BoardVO board = BoardService.getBoard(fno);
		
		model.addAttribute("board",board);
	}
	
	@PostMapping("/modify")
	public String modifyPost(BoardModifyCommand modifyCommand)throws Exception{
		
		String url = "/community/modify_success";
		
		BoardVO board = modifyCommand.toBoardVO();
		board.setTitle(HTMLInputFilter.htmlSpecialChars(board.getTitle()));
		
		BoardService.modify(board);
		
		return url;		
	}
	
	@GetMapping("/remove")
	public String remove(int fno)throws Exception{
		String url="/community/remove_success";		
		
		BoardService.remove(fno);
		
		return url;
	}
	
	@GetMapping("/reportBoard")
	public void reportForm(int fno, Model model)throws Exception{
		BoardVO board = BoardService.getBoard(fno);
		
		model.addAttribute("report",board);
	}

}
